package com.orderbook

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListMap

class SimpleOrderBook : OrderBook {
    private val buyOrders: MutableMap<Double, LinkedList<Order>> = ConcurrentSkipListMap(Comparator.reverseOrder())
    private val sellOrders: MutableMap<Double, LinkedList<Order>> = ConcurrentSkipListMap()
    private val ordersMap: MutableMap<Long, Order> = ConcurrentHashMap()

    override fun addOrder(order: Order) {
        when (order.side) {
            'B' -> addOrder(order, buyOrders)
            'O' -> addOrder(order, sellOrders)
            else -> throw IllegalArgumentException("Unknown side " + order.side)
        }
    }

    override fun modifyOrder(orderId: Long, size: Long) {
        val order = ordersMap[orderId]
        order?.let {
            if (it.side == 'B') {
                updateOrder(order, size, buyOrders)
            } else if (it.side == 'O') {
                updateOrder(order, size, sellOrders)
            }
        }
    }

    override fun removeOrder(orderId: Long) {
        val order = ordersMap[orderId]
        order?.let {
            if (it.side == 'B') {
                removeOrder(order, buyOrders)
            } else if (it.side == 'O') {
                removeOrder(order, sellOrders)
            }
        }
    }

    override fun getPrice(side: Char, level: Int): Double {
        return when (side) {
            'B' -> getPrice(buyOrders, level)
            'O' -> getPrice(sellOrders, level)
            else -> throw IllegalArgumentException("Unknown side $side")
        }
    }

    override fun getTotalSize(side: Char, level: Int): Long {
        return when (side) {
            'B' -> getTotalSize(buyOrders, level)
            'O' -> getTotalSize(sellOrders, level)
             else -> throw IllegalArgumentException("Unknown side $side")
        }
    }

    override fun getOrders(side: Char): List<Order> {
        return when (side) {
            'B' -> buyOrders.values.stream().flatMap { it.stream() }.toList()
            'O' -> sellOrders.values.stream().flatMap { it.stream() }.toList()
             else -> throw IllegalArgumentException("Unknown side $side")
        }
    }

    private fun addOrder(order: Order, orders: MutableMap<Double, LinkedList<Order>>) {
        ordersMap.compute(order.id) { _: Long, _: Order? ->
            val ordersAtPrice = orders.computeIfAbsent(order.price) { _: Double -> LinkedList() }
            ordersAtPrice.add(order)
            order
        }
    }

    private fun updateOrder(order: Order, size: Long, orders: Map<Double, LinkedList<Order>>) {
        ordersMap.compute(order.id) { _: Long, _: Order? ->
            val ordersAtPrice = orders[order.price]!!
            ordersAtPrice.remove(order)
            val newOrder = Order(order.id, order.price, order.side, size)
            ordersAtPrice.add(newOrder)
            newOrder
        }
    }

    private fun removeOrder(order: Order, orders: MutableMap<Double, LinkedList<Order>>) {
        ordersMap.compute(order.id) { _: Long, _: Order? ->
            val ordersAtPrice = orders[order.price]!!
            if (ordersAtPrice.size == 1) {
                orders.remove(order.price)
            } else {
                ordersAtPrice.remove(order)
            }
            null
        }
    }

    private fun getPrice(orders: Map<Double, LinkedList<Order>>, level: Int): Double {
        if (level > 0 && level <= orders.size) {
            val orderItr = orders.keys.iterator()
            for (i in 0 until level - 1) {
                orderItr.next()
            }
            return orderItr.next()
        }
        return 0.0
    }

    private fun getTotalSize(orders: Map<Double, LinkedList<Order>>, level: Int): Long {
        if (level > 0 && level <= orders.size) {
            val orderItr = orders.values.iterator()
            for (i in 0 until level - 1) {
                orderItr.next()
            }
            return orderItr.next().stream().mapToLong { obj: Order -> obj.size }.sum()
        }
        return 0
    }

}