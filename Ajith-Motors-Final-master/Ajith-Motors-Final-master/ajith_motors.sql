-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 22, 2019 at 04:55 AM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.1.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ajith_motors`
--
CREATE DATABASE IF NOT EXISTS `ajith_motors` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ajith_motors`;

-- --------------------------------------------------------

--
-- Table structure for table `advance_payments`
--

CREATE TABLE `advance_payments` (
  `payment_date` date NOT NULL,
  `advance_no` varchar(11) NOT NULL,
  `payment_method` varchar(15) NOT NULL,
  `reference` varchar(30) NOT NULL,
  `amount` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `advance_payments`
--

INSERT INTO `advance_payments` (`payment_date`, `advance_no`, `payment_method`, `reference`, `amount`) VALUES
('2018-12-11', 'AD201800001', 'Cheque', 'ABC123', '5000.00'),
('2018-12-11', 'AD201800001', 'Cash', '', '5000.00'),
('2018-12-11', 'AD201800002', 'Cash', '', '5000.00'),
('2018-12-12', 'AD201800003', 'Cheque', 'ABC123', '10000.00'),
('2018-12-12', 'AD201800003', 'Cash', '', '5000.00'),
('2018-12-12', 'AD201800003', 'Credit Card', '', '5000.00'),
('2018-12-12', 'AD201800004', 'Cash', '', '100.00'),
('2019-01-17', 'AD201900005', 'Cheque', '', '50000.00'),
('2019-01-17', 'AD201900004', 'Cheque', '', '50000.00'),
('2019-01-17', 'AD201900003', 'Cash', '', '10000.00'),
('2019-01-08', 'AD201900001', 'Cheque', '', '1000.00'),
('2019-01-17', 'AD201900006', 'Cash', '', '1000.00'),
('2019-01-17', 'AD201900007', 'Cash', '', '1000.00'),
('2019-01-17', 'AD201900008', 'Cash', '', '5000.00'),
('2019-01-17', 'AD201900009', 'Cash', '', '5000.00'),
('2019-01-20', 'AD201900010', 'Cash', '', '500.00');

-- --------------------------------------------------------

--
-- Table structure for table `advance_transactions`
--

CREATE TABLE `advance_transactions` (
  `advance_no` varchar(11) NOT NULL,
  `advance_date` date NOT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  `contact_no` varchar(10) DEFAULT NULL,
  `vehicle_no` varchar(15) DEFAULT NULL,
  `item_code` varchar(5) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `advance_transactions`
--

INSERT INTO `advance_transactions` (`advance_no`, `advance_date`, `customer_name`, `contact_no`, `vehicle_no`, `item_code`, `quantity`, `net_price`) VALUES
('AD201800001', '2018-12-22', 'Mr. ABC', '0123456789', 'ABC', '001', '10.00', '10000.00'),
('AD201800001', '2018-12-22', 'Mr. ABC', '0123456789', 'ABC', '002', '10.00', '5000.00'),
('AD201800002', '2018-12-13', 'Mr. Silva', '', '', '001', '1.00', '1000.00'),
('AD201800002', '2018-12-13', 'Mr. Silva', '', '', '002', '1.00', '500.00'),
('AD201800003', '2018-12-13', 'Mr. Perera', '0728463975', 'CAA-1234', '001', '10.00', '10000.00'),
('AD201800003', '2018-12-13', 'Mr. Perera', '0728463975', 'CAA-1234', '002', '1.00', '500.00'),
('AD201800004', '2018-12-12', 'Mr. Fernando', '', '', '001', '1.00', '1000.00'),
('AD201900001', '2019-01-08', 'Mr. ', '', '', '002', '2.00', '1000.00'),
('AD201900001', '2019-01-08', 'Mr. ', '', '', '001', '2.00', '2000.00'),
('AD201900002', '2019-01-17', 'Mr. ', '', '', '001', '50.00', '50000.00'),
('AD201900002', '2019-01-17', 'Mr. ', '', '', '002', '50.00', '25000.00'),
('AD201900003', '2019-01-17', 'Mr. ', '', '', '001', '100.00', '100000.00'),
('AD201900003', '2019-01-17', 'Mr. ', '', '', '002', '100.00', '50000.00'),
('AD201900003', '2019-01-17', 'Mr. ', '', '', '003', '100.00', '20000.00'),
('AD201900004', '2019-01-17', 'Mr. ', '', '', '001', '100.00', '100000.00'),
('AD201900004', '2019-01-17', 'Mr. ', '', '', '002', '100.00', '50000.00'),
('AD201900004', '2019-01-17', 'Mr. ', '', '', '003', '100.00', '20000.00'),
('AD201900005', '2019-01-17', 'Mr. ', '', '', '002', '10.00', '5000.00'),
('AD201900005', '2019-01-17', 'Mr. ', '', '', '003', '10.00', '2000.00'),
('AD201900006', '2019-01-17', 'Mr. ', '', '', '002', '10.00', '5000.00'),
('AD201900006', '2019-01-17', 'Mr. ', '', '', '003', '10.00', '2000.00'),
('AD201900007', '2019-01-17', 'Mr. ', '', '', '001', '10.00', '10000.00'),
('AD201900007', '2019-01-17', 'Mr. ', '', '', '002', '10.00', '5000.00'),
('AD201900008', '2019-01-17', 'Mr. ', '', '', '001', '10.00', '10000.00'),
('AD201900008', '2019-01-17', 'Mr. ', '', '', '002', '10.00', '5000.00'),
('AD201900009', '2019-01-20', 'Mr. ', '', '', '003', '23.00', '4600.00'),
('AD201900009', '2019-01-20', 'Mr. ', '', '', '002', '2.00', '1000.00');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`) VALUES
(1, 'Item Based'),
(2, 'Length Based');

-- --------------------------------------------------------

--
-- Table structure for table `closing_stock`
--

CREATE TABLE `closing_stock` (
  `csdate` datetime NOT NULL,
  `csamount` decimal(10,0) NOT NULL,
  `closingcash` decimal(10,0) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `closing_stock`
--

INSERT INTO `closing_stock` (`csdate`, `csamount`, `closingcash`) VALUES
('2019-01-14 17:21:59', '140000', '0');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_no` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`name`, `address`, `phone_no`) VALUES
('Ajith Motors (Pvt) Ltd.', 'No. 59/A T. B. Jayah Mawatha, Colombo 10.', '0112123124');

-- --------------------------------------------------------

--
-- Table structure for table `drafts`
--

CREATE TABLE `drafts` (
  `draft_id` varchar(11) NOT NULL,
  `draft_date` date NOT NULL,
  `item_code` varchar(5) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drafts`
--

INSERT INTO `drafts` (`draft_id`, `draft_date`, `item_code`, `quantity`, `net_price`) VALUES
('DR201800002', '2018-12-10', '002', '2.00', '1000.00'),
('DR201800002', '2018-12-10', '001', '2.00', '2000.00'),
('DR201800001', '2018-12-12', '001', '10.00', '10000.00'),
('DR201800003', '2018-12-10', '001', '1.00', '1000.00'),
('DR201800003', '2018-12-10', '002', '1.00', '500.00'),
('DR201800001', '2018-12-12', '002', '4.00', '2000.00'),
('DR201800005', '2019-01-20', '002', '2.00', '1000.00'),
('DR201800006', '2019-01-20', '001', '5.00', '5000.00'),
('DR201800006', '2019-01-20', '003', '5.00', '1000.00'),
('DR201800006', '2019-01-20', '002', '5.00', '2500.00');

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE `expenses` (
  `expense_id` varchar(11) NOT NULL,
  `expense_date` date NOT NULL,
  `expense_category` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`expense_id`, `expense_date`, `expense_category`, `description`, `amount`) VALUES
('EX201900002', '2019-01-08', 'Utility', 'LECO BILL', '2000.00'),
('EX201900001', '2019-01-07', 'Salary', 'Wages', '20000.00'),
('EX201900003', '2019-01-08', 'Salary', 'Wages', '48000.00'),
('EX201900004', '2019-01-09', 'Salary', 'Wages', '40000.00'),
('EX201900005', '2019-01-11', 'Sundry', 'Expense', '4000.00'),
('EX201900006', '2019-01-15', 'Sundry', 'Expense', '4000.00'),
('EX201900007', '2019-01-20', 'Transport', 'Asdsf	', '1000.00');

-- --------------------------------------------------------

--
-- Table structure for table `expense_category`
--

CREATE TABLE `expense_category` (
  `category_name` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expense_category`
--

INSERT INTO `expense_category` (`category_name`) VALUES
('Bank'),
('CMC'),
('Drawings'),
('Food'),
('Salary'),
('Sundry'),
('Transport'),
('Utility');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `admin_privileges` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`first_name`, `last_name`, `username`, `password`, `email_address`, `admin_privileges`) VALUES
('Admin', '', 'admin', 'admin', 'abc.ferdinando1@gmail.com', 1),
('Staff', '', 'staff', 'staff', 'nandula.2017260@iit.ac.lk', 0),
('Cashier', '', 'cashier', 'cashier', 'abc.ferdinando1@gmail.com', 0);

-- --------------------------------------------------------

--
-- Table structure for table `opening_stock`
--

CREATE TABLE `opening_stock` (
  `osdate` datetime NOT NULL,
  `osamount` decimal(10,0) NOT NULL,
  `openingcash` decimal(10,0) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `opening_stock`
--

INSERT INTO `opening_stock` (`osdate`, `osamount`, `openingcash`) VALUES
('2019-01-14 17:22:00', '140000', '0'),
('2019-01-14 17:21:00', '140000', '0');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_date` date NOT NULL,
  `invoice_no` varchar(9) NOT NULL,
  `payment_method` varchar(15) NOT NULL,
  `reference` varchar(30) NOT NULL,
  `amount` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_date`, `invoice_no`, `payment_method`, `reference`, `amount`) VALUES
('2019-01-11', '201900011', 'Cash', '', '1700.00'),
('2019-01-11', '201900010', 'Cash', '', '100.00'),
('2019-01-09', '201900009', 'Cash', '', '70000.00'),
('2019-01-09', '201900009', 'Cheque', 'ABCA123', '100000.00'),
('2019-01-09', '201900008', 'Cash', '', '7000.00'),
('2019-01-09', '201900007', 'Cash', '', '1000.00'),
('2019-01-09', '201900007', 'Cheque', '', '1000.00'),
('2019-01-08', '201900006', 'Cheque', '', '200000.00'),
('2019-01-08', '201900006', 'Cash', '', '1000000.00'),
('2019-01-08', '201900005', 'Cheque', '', '20000.00'),
('2019-01-08', '201900005', 'Cash', '', '50000.00'),
('2019-01-07', '201900004', 'Credit', '', '50000.00'),
('2019-01-07', '201900004', 'Cash', '', '50000.00'),
('2019-01-07', '201900003', 'Cash', '', '24000.00'),
('2019-01-01', '201900002', 'Credit', '', '2000.00'),
('2019-01-01', '201900002', 'Cheque', '', '10000.00'),
('2019-01-01', '201900001', 'Cash', '', '2000.00'),
('2018-12-31', '201800005', 'Cash', '', '2000.00'),
('2018-12-31', '201800004', 'Credit', '', '50000.00'),
('2018-12-31', '201800004', 'Cheque', '', '100000.00'),
('2018-12-31', '201800003', 'Cash', '', '2000.00'),
('2018-12-31', '201800002', 'Cheque', 'ABC123', '10000.00'),
('2018-12-30', '201800001', 'Cash', '', '7000.00'),
('2019-01-14', '201900015', 'Cheque', '', '5000.00'),
('2019-01-11', '201900014', 'Cash', '', '1700.00'),
('2019-01-11', '201900013', 'Cash', '', '1700.00'),
('2019-01-11', '201900012', 'Cash', '', '1700.00'),
('2019-01-14', '201900015', 'Credit', '', '5000.00'),
('2019-01-14', '201900015', 'Cash', '', '2000.00'),
('2019-01-14', '201900016', 'Cheque', 'ABC234', '5000.00'),
('2019-01-14', '201900016', 'Credit', '', '5000.00'),
('2019-01-14', '201900016', 'Cash', '', '4000.00'),
('2019-01-14', '201900017', 'Cheque', '', '20000.00'),
('2019-01-14', '201900017', 'Credit', '', '15000.00'),
('2019-01-14', '201900017', 'Cash', '', '5000.00'),
('2019-01-14', '201900018', 'Cheque', 'ABC123', '20000.00'),
('2019-01-14', '201900018', 'Credit Card', '', '10000.00'),
('2019-01-14', '201900018', 'Credit', '', '14000.00'),
('2019-01-14', '201900018', 'Cash', '', '9000.00'),
('2019-01-15', '201900019', 'Cash', '', '60000.00'),
('2019-01-15', '201900020', 'Cash', '', '10000.00'),
('2019-01-15', '201900020', 'Cheque', 'ABC1234', '10000.00'),
('2019-01-15', '201900020', 'Credit', '', '31000.00'),
('2019-01-15', '201900021', 'Cash', '', '10000.00'),
('2019-01-15', '201900021', 'Cheque', 'ABC1234', '10000.00'),
('2019-01-15', '201900021', 'Credit', '', '31000.00'),
('2019-01-15', '201900022', 'Cash', '', '1500.00'),
('2019-01-15', '201900023', 'Cheque', '', '1000.00'),
('2019-01-15', '201900023', 'Cash', '', '2000.00'),
('2019-01-20', '201900025', 'Credit Card', '', '15000.00'),
('2019-01-20', '201900025', 'Cash', '', '5000.00'),
('2019-01-20', '201900025', 'Cheque', '', '3000.00'),
('2019-01-20', '201900026', 'Cash', '', '9000.00'),
('2019-01-20', '201900027', 'Cash', '', '6300.00'),
('2019-01-20', '201900028', 'Cash', '', '5000.00'),
('2019-03-07', '201900029', 'Cash', '', '5000.00'),
('2019-03-07', '201900029', 'Credit Card', '', '1500.00'),
('2019-03-07', '201900030', 'Credit Card', '', '6500.00'),
('2019-03-07', '201900031', 'Cash', '', '2500.00'),
('2019-03-07', '201900032', 'Cash', '', '600.00'),
('2019-03-08', '201900033', 'Cash', '', '4000.00'),
('2019-03-08', '201900034', 'Cash', '', '2000.00');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `item_code` varchar(5) NOT NULL,
  `description` varchar(50) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `buying_price` decimal(10,2) NOT NULL,
  `bargain_price` decimal(10,2) NOT NULL,
  `selling_price` decimal(10,2) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`item_code`, `description`, `quantity`, `buying_price`, `bargain_price`, `selling_price`, `category_id`) VALUES
('001', 'Tyres', '15.00', '900.00', '950.00', '1000.00', 1),
('002', 'Carpets', '5.50', '400.00', '450.00', '500.00', 2),
('003', 'Lights', '439.00', '500.00', '150.00', '200.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `purchase_invoice_no` varchar(50) NOT NULL,
  `purchase_date` date NOT NULL,
  `supplier_id` varchar(20) NOT NULL,
  `item_code` varchar(5) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`purchase_invoice_no`, `purchase_date`, `supplier_id`, `item_code`, `unit_price`, `quantity`, `net_price`) VALUES
('1234', '2018-12-21', '002', '001', '900.00', '100.00', '90000.00'),
('1234', '2018-12-21', '002', '002', '400.00', '100.00', '40000.00'),
('1234', '2018-12-21', '002', '003', '100.00', '200.00', '20000.00'),
('123456', '2018-12-01', '002', '001', '900.00', '100.00', '90000.00'),
('123456', '2018-12-01', '002', '002', '400.00', '100.00', '40000.00'),
('123456', '2018-12-01', '002', '003', '100.00', '100.00', '10000.00'),
('1212', '2018-12-15', '004', '001', '900.00', '100.00', '90000.00'),
('1234', '2019-01-15', '005', '002', '400.00', '10.00', '4000.00'),
('1234', '2019-01-15', '005', '001', '9000.00', '5.00', '45000.00'),
('5569', '2019-01-20', '005', '002', '400.00', '20.50', '8200.00'),
('5569', '2019-01-20', '005', '003', '500.00', '6.00', '3000.00'),
('123456789', '2019-01-20', '002', '003', '500.00', '5.00', '2500.00');

-- --------------------------------------------------------

--
-- Table structure for table `purchasesreturn`
--

CREATE TABLE `purchasesreturn` (
  `prcode` varchar(11) NOT NULL,
  `purchase_invoice_no` varchar(50) NOT NULL,
  `return_date` date NOT NULL,
  `item_code` varchar(5) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchasesreturn`
--

INSERT INTO `purchasesreturn` (`prcode`, `purchase_invoice_no`, `return_date`, `item_code`, `quantity`, `unit_price`, `net_price`) VALUES
('SR201900001', '1234', '2019-01-20', '001', '100.00', '900.00', '90000.00'),
('SR201900002', '1234', '2019-01-20', '001', '100.00', '900.00', '90000.00'),
('SR201900003', '1234', '2019-01-20', '001', '20.00', '900.00', '18000.00'),
('SR201900004', '1234', '2019-01-20', '001', '50.00', '900.00', '45000.00'),
('SR201900005', '1234', '2019-01-20', '003', '200.00', '100.00', '20000.00'),
('SR201900006', '1234', '2019-01-20', '002', '50.00', '400.00', '20000.00');

-- --------------------------------------------------------

--
-- Table structure for table `salesreturn`
--

CREATE TABLE `salesreturn` (
  `srcode` varchar(11) NOT NULL,
  `sales_invoice_no` varchar(11) NOT NULL,
  `return_date` date NOT NULL,
  `item_code` varchar(5) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salesreturn`
--

INSERT INTO `salesreturn` (`srcode`, `sales_invoice_no`, `return_date`, `item_code`, `quantity`, `unit_price`, `net_price`) VALUES
('SR201900002', '201800001', '2019-01-16', '001', '4.00', '1000.00', '4000.00'),
('SR201900001', '201800001', '2019-01-16', '001', '4.00', '1000.00', '4000.00'),
('SR201900003', '201800001', '2019-01-16', '001', '4.00', '1000.00', '4000.00'),
('SR201900003', '201800001', '2019-01-16', '003', '3.00', '200.00', '600.00'),
('SR201900004', '201900001', '2019-01-20', '002', '1.00', '500.00', '500.00'),
('SR201900005', '201900001', '2019-01-20', '001', '1.00', '1000.00', '1000.00');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplier_id` varchar(20) NOT NULL,
  `supplier_name` varchar(100) NOT NULL,
  `supplier_address` varchar(100) DEFAULT NULL,
  `supplier_contact_no` varchar(50) DEFAULT NULL,
  `supplier_email` varchar(100) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `supplier_name`, `supplier_address`, `supplier_contact_no`, `supplier_email`) VALUES
('001', 'Auto Care', NULL, NULL, NULL),
('002', 'Sun Lanka', NULL, NULL, NULL),
('003', 'Tokyo', NULL, NULL, NULL),
('004', 'A to Z - Direct', NULL, NULL, NULL),
('005', 'A to Z - Nishantha', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `transaction_date` date NOT NULL,
  `item_code` varchar(5) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `net_price` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `transaction_date`, `item_code`, `quantity`, `net_price`) VALUES
(201800001, '2018-12-30', '001', '4.00', '4000.00'),
(201800001, '2018-12-30', '002', '2.00', '1000.00'),
(201800001, '2018-12-30', '003', '5.00', '1000.00'),
(201800002, '2018-12-31', '001', '4.00', '4000.00'),
(201800002, '2018-12-31', '002', '10.00', '5000.00'),
(201800003, '2018-12-31', '001', '1.00', '1000.00'),
(201800003, '2018-12-31', '002', '1.00', '500.00'),
(201800004, '2018-12-31', '001', '100.00', '100000.00'),
(201800004, '2018-12-31', '002', '100.00', '50000.00'),
(201800005, '2018-12-31', '001', '1.00', '1000.00'),
(201800005, '2018-12-31', '002', '1.00', '500.00'),
(201900001, '2019-01-01', '001', '1.00', '1000.00'),
(201900001, '2019-01-01', '002', '1.00', '500.00'),
(201900002, '2019-01-01', '001', '10.00', '10000.00'),
(201900002, '2019-01-01', '003', '10.00', '2000.00'),
(201900003, '2019-01-07', '001', '10.00', '10000.00'),
(201900003, '2019-01-07', '003', '20.00', '4000.00'),
(201900003, '2019-01-07', '002', '20.00', '10000.00'),
(201900004, '2019-01-07', '001', '100.00', '100000.00'),
(201900005, '2019-01-08', '001', '20.00', '20000.00'),
(201900005, '2019-01-08', '002', '100.00', '50000.00'),
(201900006, '2019-01-08', '001', '1000.00', '1000000.00'),
(201900006, '2019-01-08', '003', '1000.00', '200000.00'),
(201900007, '2019-01-09', '001', '2.00', '2000.00'),
(201900007, '2019-01-09', '002', '2.00', '1000.00'),
(201900008, '2019-01-09', '002', '10.00', '5000.00'),
(201900008, '2019-01-09', '003', '10.00', '2000.00'),
(201900009, '2019-01-09', '001', '100.00', '100000.00'),
(201900009, '2019-01-09', '003', '100.00', '20000.00'),
(201900009, '2019-01-09', '002', '100.00', '50000.00'),
(201900010, '2019-01-11', '001', '10.00', '10000.00'),
(201900010, '2019-01-11', '003', '10.00', '2000.00'),
(201900011, '2019-01-11', '001', '1.00', '1000.00'),
(201900011, '2019-01-11', '002', '1.00', '500.00'),
(201900011, '2019-01-11', '003', '1.00', '200.00'),
(201900012, '2019-01-11', '001', '1.00', '1000.00'),
(201900012, '2019-01-11', '002', '1.00', '500.00'),
(201900012, '2019-01-11', '003', '1.00', '200.00'),
(201900013, '2019-01-11', '001', '1.00', '1000.00'),
(201900013, '2019-01-11', '002', '1.00', '500.00'),
(201900013, '2019-01-11', '003', '1.00', '200.00'),
(201900014, '2019-01-11', '001', '1.00', '1000.00'),
(201900014, '2019-01-11', '002', '1.00', '500.00'),
(201900014, '2019-01-11', '003', '1.00', '200.00'),
(201900015, '2019-01-14', '001', '5.00', '5000.00'),
(201900015, '2019-01-14', '003', '10.00', '2000.00'),
(201900015, '2019-01-14', '002', '10.00', '5000.00'),
(201900016, '2019-01-14', '002', '20.00', '10000.00'),
(201900016, '2019-01-14', '003', '20.00', '4000.00'),
(201900017, '2019-01-14', '001', '30.00', '30000.00'),
(201900017, '2019-01-14', '003', '50.00', '10000.00'),
(201900018, '2019-01-14', '001', '20.00', '20000.00'),
(201900018, '2019-01-14', '003', '40.00', '8000.00'),
(201900018, '2019-01-14', '002', '50.00', '25000.00'),
(201900019, '2019-01-15', '001', '10.00', '50000.00'),
(201900019, '2019-01-15', '003', '5.00', '1000.00'),
(201900020, '2019-01-15', '001', '10.00', '50000.00'),
(201900020, '2019-01-15', '003', '5.00', '1000.00'),
(201900021, '2019-01-15', '001', '10.00', '50000.00'),
(201900021, '2019-01-15', '003', '5.00', '1000.00'),
(201900022, '2019-01-15', '001', '1.00', '1000.00'),
(201900022, '2019-01-15', '002', '1.00', '500.00'),
(201900023, '2019-01-15', '001', '1.00', '1000.00'),
(201900023, '2019-01-15', '002', '1.00', '500.00'),
(201900023, '2019-01-15', '003', '1.00', '200.00'),
(201900024, '2019-01-20', '001', '2.00', '2000.00'),
(201900024, '2019-01-20', '002', '1.00', '5000.00'),
(201900024, '2019-01-20', '003', '3.00', '600.00'),
(201900024, '2019-01-20', 'SC', '1.00', '5020.00'),
(201900025, '2019-01-20', '001', '10.00', '10000.00'),
(201900025, '2019-01-20', '003', '20.00', '4000.00'),
(201900025, '2019-01-20', 'SC', '1.00', '5000.00'),
(201900026, '2019-01-20', '001', '3.00', '3000.00'),
(201900026, '2019-01-20', '002', '2.00', '1000.00'),
(201900026, '2019-01-20', '003', '23.00', '4600.00'),
(201900027, '2019-01-20', '002', '2.00', '1000.00'),
(201900027, '2019-01-20', '003', '23.00', '4600.00'),
(201900028, '2019-01-20', '001', '5.00', '5000.00'),
(201900029, '2019-03-07', '002', '1.00', '500.00'),
(201900029, '2019-03-07', '001', '6.00', '6000.00'),
(201900030, '2019-03-07', '002', '1.00', '500.00'),
(201900030, '2019-03-07', '001', '6.00', '6000.00'),
(201900031, '2019-03-07', '002', '5.00', '2500.00'),
(201900032, '2019-03-07', '003', '3.00', '600.00'),
(201900033, '2019-03-08', '001', '4.00', '4000.00'),
(201900034, '2019-03-08', '002', '4.00', '2000.00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advance_payments`
--
ALTER TABLE `advance_payments`
  ADD PRIMARY KEY (`payment_date`,`advance_no`,`payment_method`);

--
-- Indexes for table `advance_transactions`
--
ALTER TABLE `advance_transactions`
  ADD PRIMARY KEY (`advance_no`,`item_code`),
  ADD KEY `FKitemcode` (`item_code`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `closing_stock`
--
ALTER TABLE `closing_stock`
  ADD PRIMARY KEY (`csdate`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `drafts`
--
ALTER TABLE `drafts`
  ADD PRIMARY KEY (`draft_id`,`item_code`),
  ADD KEY `FKitemcode` (`item_code`);

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`expense_id`);

--
-- Indexes for table `expense_category`
--
ALTER TABLE `expense_category`
  ADD PRIMARY KEY (`category_name`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `opening_stock`
--
ALTER TABLE `opening_stock`
  ADD PRIMARY KEY (`osdate`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`invoice_no`,`payment_method`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`item_code`),
  ADD KEY `FKproduct` (`category_id`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`purchase_invoice_no`,`supplier_id`,`item_code`),
  ADD KEY `FKitemcode` (`item_code`);

--
-- Indexes for table `purchasesreturn`
--
ALTER TABLE `purchasesreturn`
  ADD PRIMARY KEY (`prcode`,`item_code`),
  ADD KEY `FKinvoiceno` (`purchase_invoice_no`),
  ADD KEY `FKitemcode` (`item_code`);

--
-- Indexes for table `salesreturn`
--
ALTER TABLE `salesreturn`
  ADD PRIMARY KEY (`srcode`,`item_code`),
  ADD KEY `FKitemcode` (`item_code`),
  ADD KEY `FKinvoiceno` (`sales_invoice_no`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplier_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`,`item_code`),
  ADD KEY `FKitemcode` (`item_code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=201900035;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `reset1` ON SCHEDULE EVERY 1 MINUTE STARTS '2019-01-14 17:21:59' ON COMPLETION NOT PRESERVE ENABLE DO INSERT INTO closing_stock VALUES(now(),(SELECT SUM(quantity*buying_price) FROM products),(SELECT (SELECT COALESCE(SUM(amount),0) FROM advance_payments WHERE payment_method='Cash' AND payment_date=DATE(now()))+(SELECT COALESCE(SUM(amount),0) FROM payments WHERE payment_method='Cash' AND payment_date=DATE(now()))-(SELECT COALESCE(SUM(amount),0) FROM expenses WHERE expense_date=DATE(now()))))$$

CREATE DEFINER=`root`@`localhost` EVENT `reset` ON SCHEDULE EVERY 1 MINUTE STARTS '2019-01-14 17:21:00' ON COMPLETION NOT PRESERVE ENABLE DO INSERT INTO opening_stock VALUES(now(),(SELECT SUM(quantity*buying_price) FROM products),(SELECT (SELECT COALESCE(SUM(amount),0) FROM advance_payments WHERE payment_method='Cash' AND payment_date=DATE(now()))+(SELECT COALESCE(SUM(amount),0) FROM payments WHERE payment_method='Cash' AND payment_date=DATE(now()))-(SELECT COALESCE(SUM(amount),0) FROM expenses WHERE expense_date=DATE(now()))))$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
