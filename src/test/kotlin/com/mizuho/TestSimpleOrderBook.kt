package com.mizuho

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestSimpleOrderBook {
    private val orderBook: OrderBook = SimpleOrderBook()

    companion object {
        const val DETA = 0.00000001
    }

    @BeforeEach
    fun setup() {
        orderBook.addOrder(Order(1L, 19.0, 'O', 8))
        orderBook.addOrder(Order(2L, 19.0, 'O', 4))
        orderBook.addOrder(Order(5L, 22.0, 'O', 7))
        orderBook.addOrder(Order(3L, 21.0, 'O', 16))
        orderBook.addOrder(Order(4L, 21.0, 'O', 1))
        orderBook.addOrder(Order(6L, 15.0, 'B', 5))
        orderBook.modifyOrder(6L, 10)
        orderBook.addOrder(Order(7L, 13.0, 'B', 20))
        orderBook.removeOrder(7L)
        orderBook.addOrder(Order(8L, 10.0, 'B', 13))
        orderBook.addOrder(Order(9L, 10.0, 'B', 13))
    }

    @Test
    fun testGetPriceForOfferLevelOne() {
        assertEquals(19.0, orderBook.getPrice('O', 1), DETA)
    }

    @Test
    fun testGetPriceForOfferLevelTwo() {
        assertEquals(21.0, orderBook.getPrice('O', 2), DETA)
    }

    @Test
    fun testGetPriceForOfferLevelThree() {
        assertEquals(22.0, orderBook.getPrice('O', 3), DETA)
    }

    @Test
    fun testGetPriceForOfferLevelFour() {
        assertEquals(0.0, orderBook.getPrice('O', 4), DETA)
    }

    @Test
    fun testGetPriceForBidLevelOne() {
        assertEquals(15.0, orderBook.getPrice('B', 1), DETA)
    }

    @Test
    fun testGetPriceForBidLevelTwo() {
        assertEquals(10.0, orderBook.getPrice('B', 2), DETA)
    }

    @Test
    fun testGetPriceForBidLevelThree() {
        assertEquals(0.0, orderBook.getPrice('B', 3), DETA)
    }

    @Test
    fun testGetTotalSizeForOfferLevelOne() {
        assertEquals(12.0, orderBook.getTotalSize('O', 1).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForOfferLevelTwo() {
        assertEquals(17.0, orderBook.getTotalSize('O', 2).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForOfferLevelThree() {
        assertEquals(7.0, orderBook.getTotalSize('O', 3).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForOfferLevelFour() {
        assertEquals(0.0, orderBook.getTotalSize('O', 4).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForBidLevelOne() {
        assertEquals(10.0, orderBook.getTotalSize('B', 1).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForBidLevelTwo() {
        assertEquals(26.0, orderBook.getTotalSize('B', 2).toDouble(), DETA)
    }

    @Test
    fun testGetTotalSizeForBidLevelThree() {
        assertEquals(0.0, orderBook.getTotalSize('B', 3).toDouble(), DETA)
    }

    @Test
    fun testGetOfferOrders() {
        val offers = orderBook.getOrders('O')
        assertEquals(5.0, offers.size.toDouble(), DETA)
        assertEquals(1.0, offers[0].id.toDouble(), DETA)
        assertEquals(2.0, offers[1].id.toDouble(), DETA)
        assertEquals(3.0, offers[2].id.toDouble(), DETA)
        assertEquals(4.0, offers[3].id.toDouble(), DETA)
        assertEquals(5.0, offers[4].id.toDouble(), DETA)
    }

    @Test
    fun testGetBidOrders() {
        val bids = orderBook.getOrders('B')
        assertEquals(3.0, bids.size.toDouble(), DETA)
        assertEquals(6.0, bids[0].id.toDouble(), DETA)
        assertEquals(8.0, bids[1].id.toDouble(), DETA)
        assertEquals(9.0, bids[2].id.toDouble(), DETA)
    }
}
