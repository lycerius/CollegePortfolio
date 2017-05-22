-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 10, 2016 at 10:15 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vigilante`
--
CREATE DATABASE vigilante;
USE vigilante;
-- --------------------------------------------------------

--
-- Table structure for table `accessories`
--

CREATE TABLE `accessories` (
  `suspect_id` int(11) NOT NULL,
  `items` varchar(5012) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accessories`
--

INSERT INTO `accessories` (`suspect_id`, `items`) VALUES
(1, 'Hat'),
(2, 'Earings'),
(3, 'Nose Piercing');

-- --------------------------------------------------------

--
-- Table structure for table `car_theft`
--

CREATE TABLE `car_theft` (
  `ID` int(11) NOT NULL,
  `Description` varchar(1024) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Model` varchar(255) DEFAULT NULL,
  `Brand` varchar(255) DEFAULT NULL,
  `Picture` blob,
  `License` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `car_theft`
--

INSERT INTO `car_theft` (`ID`, `Description`, `Type`, `Model`, `Brand`, `Picture`, `License`) VALUES
(1, 'Blue Car', 'Toyota', 'Carolla', 'SE', NULL, 'AAA-BB-CCC'),
(2, 'Black Minivan', 'Chevy', 'Venture', 'LE', NULL, 'CCC-DD-EEE');

-- --------------------------------------------------------

--
-- Table structure for table `commit`
--

CREATE TABLE `commit` (
  `Crime_ID` int(11) NOT NULL,
  `Suspect_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commit`
--

INSERT INTO `commit` (`Crime_ID`, `Suspect_ID`) VALUES
(1, 2),
(1, 3),
(2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `crime`
--

CREATE TABLE `crime` (
  `ID` int(11) NOT NULL,
  `Description` varchar(1024) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `crime`
--

INSERT INTO `crime` (`ID`, `Description`, `Type`) VALUES
(1, 'Person stole my car', 'CAR_THEFT'),
(2, 'Someone is playing GTAV with my real car', 'CAR_THEFT');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `ID` int(11) NOT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `Street_Name` varchar(255) DEFAULT NULL,
  `BuildingNumber` varchar(255) DEFAULT NULL,
  `ZipCode` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`ID`, `x`, `y`, `Street_Name`, `BuildingNumber`, `ZipCode`) VALUES
(1, 6, 7, 'Helo Street', '23', '01855'),
(2, 8, 9, '2nd street', '56', '01853'),
(3, 6, 7, 'Helo Street', '23', '01855'),
(4, 4, 5, 'Bad Street', '8', '01854'),
(5, 2, 1, 'Cool Street', '0', '01854');

-- --------------------------------------------------------

--
-- Table structure for table `police_department`
--

CREATE TABLE `police_department` (
  `ID` int(11) NOT NULL,
  `Name` varchar(1024) DEFAULT NULL,
  `location_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `police_department`
--

INSERT INTO `police_department` (`ID`, `Name`, `location_id`, `User_id`) VALUES
(1, 'Stationary Police Department', 1, 4),
(2, 'Police Department Headquarters', 2, 1),
(3, 'Stationary Police Department', 3, 3),
(4, 'Police Box', 4, 2),
(5, 'Stationary Police Department', 5, 3);

-- --------------------------------------------------------

--
-- Table structure for table `privilege`
--

CREATE TABLE `privilege` (
  `ID` int(11) NOT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Description` varchar(5012) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `privilege`
--

INSERT INTO `privilege` (`ID`, `Type`, `Description`) VALUES
(1, 'Guest', 'Cannot do anything on database unless registered'),
(2, 'Admin', 'Can do anything on database'),
(3, 'User', 'Can do user things on database');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `RID` int(11) NOT NULL,
  `Description` varchar(5012) DEFAULT NULL,
  `Time` int(11) DEFAULT NULL,
  `crime_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`RID`, `Description`, `Time`, `crime_id`, `location_id`, `user_id`) VALUES
(1, 'Assault & Battery Charges, Car Theft', 1, 1, 2, 1),
(2, 'Assault & Battery', 2, 2, 1, 1),
(3, '1st Degree Murder, and Robbery', 3, 1, 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `robbery`
--

CREATE TABLE `robbery` (
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `suspect`
--

CREATE TABLE `suspect` (
  `ID` int(11) NOT NULL,
  `Race` varchar(255) DEFAULT NULL,
  `Gender` varchar(6) DEFAULT NULL,
  `LAST_SEEN` varchar(255) DEFAULT NULL,
  `Height` int(11) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `suspect`
--

INSERT INTO `suspect` (`ID`, `Race`, `Gender`, `LAST_SEEN`, `Height`, `Name`) VALUES
(1, 'White', 'Male', '10/10/2010', 120, 'Bad Person 1'),
(2, 'White', 'Female', '10/11/2010', 105, 'Bad Person 2'),
(3, 'Black', 'Male', '10/09/2010', 143, 'Bad Person 3');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `EMail` varchar(1024) DEFAULT NULL,
  `Name` varchar(1024) DEFAULT NULL,
  `PD_ID` int(11) DEFAULT NULL,
  `salt` int(11) NOT NULL,
  `pwh` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `EMail`, `Name`, `PD_ID`, `salt`, `pwh`) VALUES
(1, 'pnc@lol.com', 'Nathan Purpura', NULL, 1346993711, 'srDprXldfFRMmkNa0ScfE/61RCMywB4FHwwYFQMcJFM='),
(2, 'test1@test.com', 'Test Testerson', NULL, 907418215, 'flzh59wguznKaX4vPpuMoXBWRYT0ELePzEvRTRO1kKk='),
(3, 'test2@test.com', 'Test Testerson', NULL, -485777132, 'gRNU0RkA9kXRGAf4HL+2Nsqat8blpLNyd8lArzAno5s='),
(4, 'test3@gmail.com', 'Testerson Test', NULL, -24996627, 'EbW8QBRIcVf75EOitBrplC0qGL/hl54vDkamUsS/IbU='),
(5, 'test4@gmail.com', 'Testerson Test T', NULL, -1104064718, 'Y+L/kwD2NUtJx70lc6XGlPGkBgeavIBxaALbGfmJEiY='),
(6, 'Hello World 3000', 'Hello World 3000', NULL, 1851552276, 'O9VSj20/2aB2npKYKdMjFm2qy9DbCmPrCC63i/Uoso0='),
(7, 'Hello World 4000', 'Hello World 4000', NULL, -1184077527, 'sa5JD1aqEFupFTjHhAQcl1djV/AIuUIaGCM0Vgq2Oh0='),
(8, 'Hello World 5000', 'Hello World 5000', NULL, 613369248, 'VlnQvy/2Ao0JAilA1ZMXqRnqKfcGT/twMgspvBVfajc=');

-- --------------------------------------------------------

--
-- Table structure for table `userspriv`
--

CREATE TABLE `userspriv` (
  `PrivID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userspriv`
--

INSERT INTO `userspriv` (`PrivID`, `UserID`) VALUES
(2, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5);

-- --------------------------------------------------------

--
-- Table structure for table `weapon`
--

CREATE TABLE `weapon` (
  `suspect_id` int(11) NOT NULL,
  `Weapon` varchar(5012) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `weapon`
--

INSERT INTO `weapon` (`suspect_id`, `Weapon`) VALUES
(1, 'Pistol Gun'),
(2, 'Pocket Knife and Baton'),
(3, 'No weapon');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accessories`
--
ALTER TABLE `accessories`
  ADD KEY `suspect_id` (`suspect_id`);

--
-- Indexes for table `car_theft`
--
ALTER TABLE `car_theft`
  ADD PRIMARY KEY (`License`),
  ADD KEY `ID` (`ID`);

--
-- Indexes for table `commit`
--
ALTER TABLE `commit`
  ADD KEY `Crime_ID` (`Crime_ID`),
  ADD KEY `Suspect_ID` (`Suspect_ID`);

--
-- Indexes for table `crime`
--
ALTER TABLE `crime`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `police_department`
--
ALTER TABLE `police_department`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `location_id` (`location_id`),
  ADD KEY `User_id` (`User_id`);

--
-- Indexes for table `privilege`
--
ALTER TABLE `privilege`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`RID`),
  ADD KEY `crime_id` (`crime_id`),
  ADD KEY `location_id` (`location_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `robbery`
--
ALTER TABLE `robbery`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `suspect`
--
ALTER TABLE `suspect`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_by_email` (`EMail`(767));

--
-- Indexes for table `userspriv`
--
ALTER TABLE `userspriv`
  ADD KEY `PrivID` (`PrivID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `weapon`
--
ALTER TABLE `weapon`
  ADD KEY `suspect_id` (`suspect_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `crime`
--
ALTER TABLE `crime`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `police_department`
--
ALTER TABLE `police_department`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `privilege`
--
ALTER TABLE `privilege`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `RID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `suspect`
--
ALTER TABLE `suspect`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `accessories`
--
ALTER TABLE `accessories`
  ADD CONSTRAINT `accessories_ibfk_1` FOREIGN KEY (`suspect_id`) REFERENCES `suspect` (`ID`);

--
-- Constraints for table `car_theft`
--
ALTER TABLE `car_theft`
  ADD CONSTRAINT `car_theft_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `crime` (`ID`);

--
-- Constraints for table `commit`
--
ALTER TABLE `commit`
  ADD CONSTRAINT `commit_ibfk_1` FOREIGN KEY (`Crime_ID`) REFERENCES `crime` (`ID`),
  ADD CONSTRAINT `commit_ibfk_2` FOREIGN KEY (`Suspect_ID`) REFERENCES `suspect` (`ID`);

--
-- Constraints for table `police_department`
--
ALTER TABLE `police_department`
  ADD CONSTRAINT `police_department_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`ID`),
  ADD CONSTRAINT `police_department_ibfk_2` FOREIGN KEY (`User_id`) REFERENCES `users` (`ID`);

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`crime_id`) REFERENCES `crime` (`ID`),
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`ID`),
  ADD CONSTRAINT `report_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`);

--
-- Constraints for table `robbery`
--
ALTER TABLE `robbery`
  ADD CONSTRAINT `robbery_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `crime` (`ID`);

--
-- Constraints for table `userspriv`
--
ALTER TABLE `userspriv`
  ADD CONSTRAINT `userspriv_ibfk_1` FOREIGN KEY (`PrivID`) REFERENCES `privilege` (`ID`),
  ADD CONSTRAINT `userspriv_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`);

--
-- Constraints for table `weapon`
--
ALTER TABLE `weapon`
  ADD CONSTRAINT `weapon_ibfk_1` FOREIGN KEY (`suspect_id`) REFERENCES `suspect` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
