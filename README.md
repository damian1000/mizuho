Java Technical Test

PART A
The following functionality was written in Kotlin by the classes Order, OrderBook, and SimpleOrderBook:

1. Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently)
2. Given an order id, remove an Order from the OrderBook (order deletions are expected to occur at approximately 60% of the rate of order additions)
3. Given an order id and a new size, modify an existing order in the book to use the new size (size modifications do not effect time priority)
4. Given a side and a level (an integer value >0) return the price for that level (where level 1 represents the best price for a given side). 
   For example, given side=B and level=2 return the second best bid price
5. Given a side and a level return the total size available for that level
6. Given a side return all the orders from that side of the book, in level- and time-order

Please look at the test class called TestSimpleOrderBook that checks whether the above functionality was implemented correctly.

PART B.
To support real-life, latency-sensitive trading operations, the following changes are suggested:

1. Add support for multiple symbols, as a business will most likely trade more than one symbol.
2. Add support for multiple threads to operate on this class in a thread-safe manner. This can be achieved by using concurrent data structures, such as ConcurrentSkipListMap and ConcurrentHashMap, to reduce latency.
3. Use the compute method when accessing a map to ensure that operations are atomic.
