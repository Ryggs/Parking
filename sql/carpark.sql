-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 16, 2017 at 02:27 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carpark`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `buy_sub` (`vLogin` VARCHAR(50), `vType` ENUM('30days','90days','180days','1year','unlimited'), `vStartTime` DATETIME)  begin
SET @vUserNo = (SELECT UserNo from user where UserLogin = vLogin);
if (@vUserNo is NOT NULL) then
	SET @vSubNo = (SELECT max(SubNo) from subscription) + 1; #Search new subscription number (SubNo)
	if(vType = '30days') then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 31 DAY));
		SET @vPrice = (SELECT prices.Price from prices where prices.Type = 'sub30days');
	elseif(vType = '90days') then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 91 DAY));
		SET @vPrice = (SELECT prices.Price from prices where prices.Type = 'sub90days');
	elseif(vType = '180days')then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 181 DAY));	
		SET @vPrice = (SELECT prices.Price from prices where prices.Type = 'sub180days');		
	elseif(vType = '1year')then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 1 YEAR));	
		SET @vPrice = (SELECT prices.Price from prices where prices.Type = 'sub1year');
	elseif(vType = 'unlimited')then
		SET @vEndTime = "9999-12-31 23:59:59";
		SET @vPrice = 0;
	end if;
	
	if(@vPrice is NOT NULL) then
		if(@vEndTime is NOT NULL) then
			INSERT INTO subscription(SubNo, StartTime, EndTime, PurchaseTime, Type, Price) VALUES(@vSubNo, vStartTime, @vEndTime, NOW(), vType, @vPrice );
			INSERT INTO user_sub(UserNo, SubNo) VALUES(@vUserNo, @vSubNo);
			SELECT "DONE" as "DONE" , "0" as "ErrType", "buy_sub" as "Fun","Subscription added correctly" as "Info";
		end if;
	else
		SELECT "ERROR" as "ERROR", "1" as "ErrType", "buy_sub" as "Fun", "Incorrect type of subscription. Subscription hasn't been added" as "Info";
	end if;
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "buy_sub" as "Fun", "This login is not correct. Subscription hasn't been added" as "Info";
end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_ticket` ()  begin
SET @vTicNo = (SELECT max(TicketNo) from ticket) + 1; #Search new ticket number (TicketNo)
SET @vEntryTime = NOW();
	
INSERT INTO ticket(TicketNo, EntryTime) VALUES(@vTicNo, @vEntryTime);
SELECT "DONE" as "DONE" , "0" as "ErrType", "get_ticket" as "Fun","New Ticket added correctly" as "Info", @vTicNo as "TicketNo", @vEntryTime as "EntryTime";

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_sub` (`vUserNo` INT)  begin
SET @vNow = Now();
SET @vSubNo = (SELECT SubNo from subscription natural join user_sub where UserNo = vUserNo and Now() between subscription.StartTime and subscription.EndTime);
if (@vSubNo is NOT NULL) then
	SELECT "DONE" as "DONE" , "0" as "ErrType", "get_user_sub" as "Fun","This user have active subscription" as "Info", @vSubNo as "SubNo";
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "get_user_sub" as "Fun", "This user has no active subscription or user incorrect" as "Info";
end if;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `new_user` (`newLogin` VARCHAR(50), `newPass` VARCHAR(50), `newPermType` ENUM('admin','user'), `newName` VARCHAR(50), `newSurname` VARCHAR(50), `newPhone` INT(9), `newEmail` VARCHAR(50))  begin
SET @n1 = (SELECT max(UserNo) from user) + 1; #Search new user number (UserNo)
SET @n2 = (SELECT UserNo from user where UserLogin = newLogin);
if (@n2 is NULL) then
	INSERT INTO user(UserNo, UserLogin, UserPass , PermType, Name, Surname, Phone, Email) VALUES(@n1, newLogin, newPass, newPermType, newName, newSurname, newPhone, newEmail );
	SELECT "DONE" as "DONE" , "0" as "ErrType", "new_user" as "Fun","User added correctly" as "Info";
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "new_user" as "Fun", "This login is in use. User hasn't been added" as "Info";
end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pay_ticket` (`vTicketNo` INT, `vPaymentType` ENUM('cash','subscription'), `vSubNo` INT)  begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	SET @vNow = Now();
	if(vPaymentType = 'cash') then
		UPDATE ticket SET PaymentType='cash' WHERE ticket.TicketNo = vTicketNo;
		UPDATE ticket SET PaymentTime=@vNow WHERE ticket.TicketNo = vTicketNo;
		SELECT "DONE" as "DONE" , "0" as "ErrType", "pay_ticket" as "Fun","Ticket charge added correctly" as "Info", @vNow as "PaymentTime", vPaymentType as "PaymentType";
	elseif(vPaymentType = 'subscription') then
		SET @vUserNo = (SELECT UserNo from user_sub where user_sub.SubNo = vSubNo);
		if(@vUserNo is NOT NULL) then
		
			#TODO ERROR This user used his subscription to pay for another ticket at the same time // zabezpieczenie przeciwcebulowe
		
		
			INSERT INTO user_ticket(TicketNo, UserNo) VALUES(vTicketNo, @vUserNo);
			UPDATE ticket SET PaymentType='subscription' WHERE ticket.TicketNo = vTicketNo;
			UPDATE ticket SET PaymentTime=@vNow WHERE ticket.TicketNo = vTicketNo;
			SELECT "DONE" as "DONE" , "0" as "ErrType", "pay_ticket" as "Fun","Ticket charge added correctly" as "Info", @vNow as "PaymentTime", vPaymentType as "PaymentType";
		else
			SELECT "ERROR" as "ERROR", "1" as "ErrType", "pay_ticket" as "Fun", "This SubNo is not correct. Ticket hasn't been paid" as "Info";
		end if;
	else
		SELECT "ERROR" as "ERROR", "1" as "ErrType", "pay_ticket" as "Fun", "This PaymentType is not correct. Ticket hasn't been paid" as "Info";
	end if;
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "pay_ticket" as "Fun", "This TicketNo is not correct. Ticket hasn't been paid" as "Info";
end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `set_ticket_charge` (`vTicketNo` INT)  begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	SET @vNow = Now();
	SET @vDuration = (SELECT ((HOUR(TIMEDIFF(@vNow, @vEntryTime)) + 1)));
	SET @vPriceHour = (SELECT prices.Price from prices where prices.Type = 'hour1');
	if( @vPriceHour is NOT NULL) then
		SET @vCharge = @vDuration * @vPriceHour;
		UPDATE ticket SET Charge=@vCharge WHERE ticket.TicketNo = vTicketNo;
		SELECT "DONE" as "DONE" , "0" as "ErrType", "set_ticket_charge" as "Fun","Ticket charge added correctly" as "Info", @vCharge as "TicketCharge", @vDuration as "DurationTime";
	else
		SELECT "ERROR" as "ERROR", "1" as "ErrType", "set_ticket_charge" as "Fun", "There is no price hour1 in table price. Charge hasn't been added" as "Info";	
	end if;
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "set_ticket_charge" as "Fun", "This TicketNo is not correct. Charge hasn't been added" as "Info";
end if;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `prices`
--

CREATE TABLE `prices` (
  `Id` int(11) NOT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `prices`
--

INSERT INTO `prices` (`Id`, `Type`, `Price`) VALUES
(1, 'hour1', 300),
(2, 'sub30days', 5000),
(3, 'sub90days', 12000),
(4, 'sub180days', 22000),
(5, 'sub1year', 40000);

-- --------------------------------------------------------

--
-- Table structure for table `subscription`
--

CREATE TABLE `subscription` (
  `SubNo` int(11) NOT NULL,
  `StartTime` datetime NOT NULL,
  `EndTime` datetime NOT NULL,
  `PurchaseTime` datetime NOT NULL,
  `Type` enum('30days','90days','180days','1year','unlimited') COLLATE utf8_unicode_ci NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `subscription`
--

INSERT INTO `subscription` (`SubNo`, `StartTime`, `EndTime`, `PurchaseTime`, `Type`, `Price`) VALUES
(1, '0001-01-01 00:00:00', '9999-12-31 23:59:59', '2017-01-01 00:00:00', 'unlimited', 0),
(2, '0001-01-01 00:00:00', '9999-12-31 23:59:59', '2017-01-01 00:00:00', 'unlimited', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `TicketNo` int(11) NOT NULL,
  `EntryTime` datetime NOT NULL,
  `LeaveTime` datetime DEFAULT NULL,
  `PaymentTime` datetime DEFAULT NULL,
  `PaymentType` enum('cash','subscription') COLLATE utf8_unicode_ci DEFAULT NULL,
  `Charge` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`TicketNo`, `EntryTime`, `LeaveTime`, `PaymentTime`, `PaymentType`, `Charge`) VALUES
(1, '2017-11-15 09:00:00', NULL, '2017-11-16 13:43:05', 'cash', 8700),
(2, '2017-11-16 01:02:51', NULL, '2017-11-16 14:17:35', 'cash', 3900);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserNo` int(11) NOT NULL,
  `UserLogin` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `UserPass` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `PermType` enum('admin','user') COLLATE utf8_unicode_ci NOT NULL,
  `Name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Surname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Phone` int(9) DEFAULT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserNo`, `UserLogin`, `UserPass`, `PermType`, `Name`, `Surname`, `Phone`, `Email`) VALUES
(1, 'admin', 'admin', 'admin', 'Admin', 'Admin', 660770880, 'admin@sql.com'),
(2, 'javapark', 'javapark', 'admin', 'Java', 'Park', 999999999, 'Java@Park.com'),
(3, 'Klos', 'Labs', 'user', 'Klos', 'Labs', 111222333, 'vip@kk');

-- --------------------------------------------------------

--
-- Table structure for table `user_sub`
--

CREATE TABLE `user_sub` (
  `UserNo` int(11) NOT NULL,
  `SubNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_sub`
--

INSERT INTO `user_sub` (`UserNo`, `SubNo`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_ticket`
--

CREATE TABLE `user_ticket` (
  `UserNo` int(11) NOT NULL,
  `TicketNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_ticket`
--

INSERT INTO `user_ticket` (`UserNo`, `TicketNo`) VALUES
(1, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `prices`
--
ALTER TABLE `prices`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`SubNo`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`TicketNo`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserNo`);

--
-- Indexes for table `user_sub`
--
ALTER TABLE `user_sub`
  ADD UNIQUE KEY `user_sub_fk_2` (`SubNo`) USING BTREE,
  ADD KEY `user_sub_fk_1` (`UserNo`);

--
-- Indexes for table `user_ticket`
--
ALTER TABLE `user_ticket`
  ADD UNIQUE KEY `user_ticket_fk_2` (`TicketNo`) USING BTREE,
  ADD KEY `user_ticket_fk_1` (`UserNo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `prices`
--
ALTER TABLE `prices`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscription`
--
ALTER TABLE `subscription`
  MODIFY `SubNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `TicketNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_sub`
--
ALTER TABLE `user_sub`
  ADD CONSTRAINT `user_sub_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`),
  ADD CONSTRAINT `user_sub_fk_2` FOREIGN KEY (`SubNo`) REFERENCES `subscription` (`SubNo`);

--
-- Constraints for table `user_ticket`
--
ALTER TABLE `user_ticket`
  ADD CONSTRAINT `user_ticket_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_ticket_fk_2` FOREIGN KEY (`TicketNo`) REFERENCES `ticket` (`TicketNo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
