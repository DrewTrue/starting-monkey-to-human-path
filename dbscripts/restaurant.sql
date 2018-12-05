-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 05, 2018 at 08:57 PM
-- Server version: 5.5.25
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `restaurant`
--

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE IF NOT EXISTS `items` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `cost` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `name`, `description`, `cost`) VALUES
(1, 'Chicken wings', 'Spicy, salty, unhealthy', 5),
(2, 'Soda', 'Sweet, unhealthy ', 2),
(3, 'Burger', 'Delicious, unhealthy', 5),
(4, 'Salad', 'Healthy', 3),
(5, 'Soup', 'With mushrooms, healthy ', 10);

-- --------------------------------------------------------

--
-- Table structure for table `items_orders`
--

CREATE TABLE IF NOT EXISTS `items_orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `orders_id` int(10) NOT NULL,
  `items_dictionary_id` int(10) NOT NULL,
  `quantity` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `items_orders`
--

INSERT INTO `items_orders` (`id`, `orders_id`, `items_dictionary_id`, `quantity`) VALUES
(1, 1, 1, 3),
(2, 1, 2, 1),
(3, 1, 4, 2),
(4, 2, 3, 1),
(5, 2, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `officiants`
--

CREATE TABLE IF NOT EXISTS `officiants` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `second_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `officiants`
--

INSERT INTO `officiants` (`id`, `first_name`, `second_name`) VALUES
(1, 'John', 'Stones'),
(2, 'Kate', 'Hudson'),
(3, 'Alice', 'Ro'),
(4, 'Bill', 'Clinton'),
(5, 'James', 'Brown');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `officiant_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `date`, `officiant_id`) VALUES
(1, '2018-12-03', 1),
(2, '2018-12-05', 3),
(3, '2018-12-05', 3),
(4, '2018-12-01', 2),
(5, '2018-11-15', 5);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
