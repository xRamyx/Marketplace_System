-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 01, 2022 at 05:23 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `marketplace`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` float NOT NULL,
  `img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `category`, `qty`, `price`, `img`) VALUES
(1, 'Eyeshadow pallet', 'Makeup', 84, 200, '1.png'),
(2, 'Essence mascara', 'Makeup', 193, 95, '2.png'),
(3, 'Aeropostale women sweatpants', 'clothing', 194, 400, '3.png'),
(4, 'Aeropostale jacket', 'clothing', 197, 1000, '4.png'),
(5, 'Aeropostale Hoodie', 'clothing', 96, 600, '5.png'),
(6, 'iphone 11 case', 'Mobile Accessories', 286, 40, '6.png'),
(7, 'joyroom power Bank', 'Mobile Accessories', 446, 155, '7.png'),
(8, 'samsung 43 inch smart LED name', 'TVs and Electronics', 399, 6650, '8.png'),
(9, 'Ball for volley', 'Sports', 499, 200, '9.jpeg'),
(10, 'juhayna juice-Guava', 'Food', 100, 5, '10.jpeg'),
(11, 'italiano pasta', 'Food', 100, 26, '11.jpeg'),
(12, 'fern pure natural butter', 'Food', 200, 60, '12.jpeg'),
(13, 'vitrac strawberry jam', 'Food', 100, 32, '13.jpeg'),
(14, 'Dettol Antiseptic ', 'Home care and cleaning', 300, 150, '14.jpeg'),
(15, 'clear shampoo +Axe Body Spray + CLOSEUP Toothpaste', 'Body care', 500, 90, '15.jpeg'),
(16, 'Tresemme pack (shampoo + conditioner)', 'Body care', 600, 110, '16.jpeg'),
(17, 'pack 550 tissues', 'Home care and cleaning', 150, 45, '17.png'),
(18, 'oxi lavender ', 'Home care and cleaning', 300, 195, '18.png'),
(19, 'Farida Spray Air Freshener', 'Home care and cleaning', 400, 30, '19.png'),
(20, 'Raid Insects Killer', 'Home care and cleaning', 800, 32, '20.png'),
(21, 'Granite size-14', 'Home and kitchen', 1000, 140, '21.png'),
(22, 'Granite cooking pot', 'Home and kitche', 50, 290, '22.png'),
(23, 'Food container', 'Home and kitche', 400, 66, '23.png'),
(24, 'stainless steel 5 pieces', 'Home and kitche', 30, 80, '24.png'),
(25, 'Fresh stand fan', 'house appliances', 250, 990, '25.png'),
(26, 'Electric Kettle ', 'house appliances', 475, 340, '26.png'),
(27, 'washing machine LG 7 kg', 'house appliances', 660, 8800, '27.png'),
(28, 'LG microwave ', 'house appliances', 700, 5650, '28.png'),
(29, 'Oppo A74', 'Mobile phones', 174, 6500, '29.png'),
(30, 'Apple iphone 13', 'Mobile phones', 260, 19000, '30.png');

-- --------------------------------------------------------

--
-- Table structure for table `purchased_prod`
--

CREATE TABLE `purchased_prod` (
  `qty` int(11) NOT NULL,
  `trans_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchased_prod`
--

INSERT INTO `purchased_prod` (`qty`, `trans_id`, `product_id`) VALUES
(1, 12, 1),
(1, 12, 2),
(1, 13, 1),
(1, 14, 2),
(1, 15, 4),
(2, 16, 1),
(2, 16, 2),
(2, 16, 3),
(1, 16, 4),
(3, 16, 5),
(1, 16, 6),
(1, 17, 3),
(1, 18, 3),
(4, 19, 7),
(1, 19, 8),
(1, 19, 9),
(1, 20, 1);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `balance_before` float NOT NULL,
  `balance_after` float NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `balance_before`, `balance_after`, `user_id`) VALUES
(12, 9610, 9315, 8),
(13, 9315, 9115, 8),
(14, 9115, 9020, 8),
(15, 9020, 8020, 8),
(16, 8020, 3790, 8),
(17, 14285, 13885, 6),
(18, 13885, 13485, 6),
(19, 13485, 6015, 6),
(20, 3790, 3590, 8);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fname` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mname` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lname` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_name` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pass` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` float DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fname`, `mname`, `lname`, `user_name`, `pass`, `balance`) VALUES
(1, 'Ramy', 'M.', 'Ahmed', 'bigramyx10', '123456', 5000),
(2, 'Rany', 'R.', 'Phillip', 'bigranyx10', '1234567', 6000),
(6, 'kirollos', 'A.', 'Ashraf', 'kirollos', '123123', 6015),
(7, 'test', 't', 'hh', 'test', 'test123', 34200),
(8, 'admin fn', 'm', 'admin ln', 'admin', 'admin', 3590);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchased_prod`
--
ALTER TABLE `purchased_prod`
  ADD PRIMARY KEY (`trans_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchased_prod`
--
ALTER TABLE `purchased_prod`
  ADD CONSTRAINT `purchased_prod_ibfk_1` FOREIGN KEY (`trans_id`) REFERENCES `transaction` (`id`),
  ADD CONSTRAINT `purchased_prod_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
