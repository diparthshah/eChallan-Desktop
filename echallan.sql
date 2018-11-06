-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2018 at 05:20 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `echallan`
--

-- --------------------------------------------------------

--
-- Table structure for table `challan`
--

CREATE TABLE `challan` (
  `challanid` varchar(20) NOT NULL,
  `officerid` varchar(20) DEFAULT NULL,
  `vehiclenumber` varchar(20) DEFAULT NULL,
  `issuedate` varchar(20) DEFAULT NULL,
  `issuetime` varchar(20) DEFAULT NULL,
  `rulesbroken` varchar(120) DEFAULT NULL,
  `fineamount` varchar(20) DEFAULT NULL,
  `paystatus` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `challan`
--

INSERT INTO `challan` (`challanid`, `officerid`, `vehiclenumber`, `issuedate`, `issuetime`, `rulesbroken`, `fineamount`, `paystatus`) VALUES
('17', '172257', 'MH02CP7394', '2018-10-03', '13:27', '[Signal Violation, No Driving Documents]', '500', '1'),
('80', '172258', 'MH02CP7394', '2018-10-03', '15:37', '[Signal Violation, No Driving Documents, Road Safety Violation]', '1200', '1'),
('92', '172256', 'MH02CP7394', '2018-10-30', '23:32', '[Signal Violation, No Driving Documents, Speed Limit Violation, Road Safety Violation]', '1700', '1');

-- --------------------------------------------------------

--
-- Table structure for table `civilian`
--

CREATE TABLE `civilian` (
  `aadharid` varchar(20) NOT NULL,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `pincode` varchar(20) DEFAULT NULL,
  `streetnum` varchar(20) DEFAULT NULL,
  `licensenum` varchar(20) DEFAULT NULL,
  `vehiclenum` varchar(20) DEFAULT NULL,
  `vehicletype` varchar(30) DEFAULT NULL,
  `vehiclemodel` varchar(20) DEFAULT NULL,
  `phonenum` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `civilian`
--

INSERT INTO `civilian` (`aadharid`, `fname`, `lname`, `pincode`, `streetnum`, `licensenum`, `vehiclenum`, `vehicletype`, `vehiclemodel`, `phonenum`) VALUES
('123456789123', 'DIPARTH', 'SHAH', '400067', '13', '148004', 'MH02CP7394', 'Two Wheeler', 'WAGONR', '9821665400'),
('123456789456', 'KINSHUK', 'SHAH', '400091', '14', '148007', 'DL02RS7394', 'Four Wheeler', 'SWIFT', '9324365400'),
('123456789789', 'PRIYANSH', 'SHAH', '400064', '15', '148009', 'GJ02AC1914', 'Commercial Transport', 'TRUCK', '9967483325');

-- --------------------------------------------------------

--
-- Table structure for table `officer`
--

CREATE TABLE `officer` (
  `officerid` varchar(20) NOT NULL,
  `officername` varchar(20) DEFAULT NULL,
  `officerdesg` varchar(20) DEFAULT NULL,
  `policestn` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `officer`
--

INSERT INTO `officer` (`officerid`, `officername`, `officerdesg`, `policestn`) VALUES
('172256', 'katekar', 'constable', 'borivali'),
('172257', 'gaitonde', 'commisoner', 'kandivali'),
('172258', 'sartaj', 'inspector', 'vasai');

-- --------------------------------------------------------

--
-- Table structure for table `rules`
--

CREATE TABLE `rules` (
  `rulename` varchar(50) DEFAULT NULL,
  `fineamount` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rules`
--

INSERT INTO `rules` (`rulename`, `fineamount`) VALUES
('signal violation', '200'),
('no driving document', '300'),
('speed limit violation', '500'),
('road safety violation', '700');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `challan`
--
ALTER TABLE `challan`
  ADD PRIMARY KEY (`challanid`);

--
-- Indexes for table `civilian`
--
ALTER TABLE `civilian`
  ADD PRIMARY KEY (`aadharid`);

--
-- Indexes for table `officer`
--
ALTER TABLE `officer`
  ADD PRIMARY KEY (`officerid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
