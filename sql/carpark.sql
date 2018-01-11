-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 11 Sty 2018, 20:35
-- Wersja serwera: 10.1.21-MariaDB
-- Wersja PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `carpark`
--

DELIMITER $$
--
-- Procedury
--
CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `buy_sub` (IN `vLogin` VARCHAR(50), IN `vType` ENUM('30days','90days','180days','1year','unlimited'), IN `vStartTime` DATETIME)  begin
SET @vUserNo = (SELECT UserNo from userparking where UserLogin = vLogin);
if (@vUserNo is NOT NULL) then
	SET @vSubNo = (SELECT max(SubNo) from subscription) + 1; 	if(vType = '30days') then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 31 DAY));
		SET @vPrice = (SELECT prices.Price from prices where prices.Name = '30days');
	elseif(vType = '90days') then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 91 DAY));
		SET @vPrice = (SELECT prices.Price from prices where prices.Name = '90days');
	elseif(vType = '180days')then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 181 DAY));	
		SET @vPrice = (SELECT prices.Price from prices where prices.Name = '180days');		
	elseif(vType = '1year')then
		SET @vEndTime = DATE(DATE_ADD(vStartTime, INTERVAL 1 YEAR));	
		SET @vPrice = (SELECT prices.Price from prices where prices.Name= '1year');
	elseif(vType = 'unlimited')then
		SET @vEndTime = "9999-12-31 23:59:59";
		SET @vPrice = 0;
	end if;
	
	if(@vPrice is NOT NULL) then
		if(@vEndTime is NOT NULL) then
			INSERT INTO subscription(SubNo, StartTime, EndTime, PurchaseTime, Type, Price) VALUES(@vSubNo, vStartTime, @vEndTime, NOW(), vType, @vPrice );
			INSERT INTO user_sub(UserNo, SubNo) VALUES(@vUserNo, @vSubNo);
			SELECT "DONE" as "Status" , "0" as "ErrType", "buy_sub" as "Fun","Subscription added correctly" as "Info";
		end if;
	else
		SELECT "ERROR" as "Status", "1" as "ErrType", "buy_sub" as "Fun", "Incorrect type of subscription. Subscription hasn't been added" as "Info";
	end if;
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "buy_sub" as "Fun", "This login is not correct. Subscription hasn't been added" as "Info";
end if;
end$$

CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `check_ticket_can_exit` (`vTicketNo` INT, `vControlCode` INT)  begin
SET @vPaymentTime = (SELECT PaymentTime from ticket where TicketNo = vTicketNo);
if (@vPaymentTime is NOT NULL) then
	SET @vControlCodeDB = (SELECT ControlCode from ticket where TicketNo = vTicketNo);
	if(vControlCode = @vControlCodeDB) then
		SET @vLeaveTime = (SELECT LeaveTime from ticket where TicketNo = vTicketNo);
		if(@vLeaveTime is NULL) then
			SET @vNow = Now();
			SET @vDuration = (SELECT (TIMEDIFF(@vNow, @vPaymentTime)));
			if(@vDuration < "00:15:00") then
								SELECT "DONE" as "Status" , "0" as "ErrType", "check_ticket_can_exit" as "Fun","Ticket LeaveTime added corectly" as "Info";
			else
				SELECT "ERROR" as "Status", "1" as "ErrType", "check_ticket_can_exit" as "Fun", "Your 15 min delay has gone. You have to pay ticket again for additional minutes" as "Info";
			end if;
		else
			SELECT "ERROR" as "Status", "1" as "ErrType", "check_ticket_can_exit" as "Fun", "This ticket has left parking. You can not open bar again" as "Info";
		end if;
	else
		SELECT "ERROR" as "Status", "1" as "ErrType", "check_ticket_can_exit" as "Fun", "This ControlCode is incorrect" as "Info";
	end if;
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "check_ticket_can_exit" as "Fun", "This TicketNo is not correct or Ticket hasn't been paid" as "Info";
end if;
end$$

CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `get_money` (`vDateFrom` DATETIME, `vDateTo` DATETIME)  begin
SET @vGetMyMoneyTic = (SELECT sum(Charge) as 'MyMoney' from ticket where ticket.PaymentType = 'cash' and ticket.PaymentTime is NOT NULL and ticket.PaymentTime between vDateFrom and vDateTo);
SET @vGetMyMoneySub = (SELECT sum(Price) as 'MyMoney' from subscription where subscription.PurchaseTime between vDateFrom and vDateTo);
	SELECT "DONE" as "Status" , "0" as "ErrType", "get_user_sub" as "Fun","YourMoney" as "Info", @vGetMyMoneyTic as "TicketMoney", @vGetMyMoneySub as "SubMoney", @vGetMyMoneyTic + @vGetMyMoneySub as "TotalMoney";
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_newest_sub` (IN `login` VARCHAR(250))  NO SQL
SELECT `userlogin`, `Name`, `Surname`, `StartTime`, `EndTime` FROM `userparking` NATURAL JOIN `user_sub` NATURAL JOIN `subscription` WHERE `userlogin` = login AND `StartTime` = (SELECT MAX(`StartTime`) FROM `subscription` NATURAL JOIN `user_sub` NATURAL JOIN `userparking` WHERE `userLogin` = login)$$

CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `get_ticket` ()  begin
SET @vTicNo = (SELECT max(TicketNo) from ticket) + 1; SET @vEntryTime = NOW();
	
INSERT INTO ticket(TicketNo, EntryTime) VALUES(@vTicNo, @vEntryTime);
SELECT "DONE" as "Status" , "0" as "ErrType", "get_ticket" as "Fun","New Ticket added correctly" as "Info", @vTicNo as "TicketNo", @vEntryTime as "EntryTime";

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_sub` (`pUserName` VARCHAR(255), `pUserPass` VARCHAR(255))  begin
SET @vNow = Now();
SET @vUserNo = (SELECT UserNo FROM userparking WHERE UserLogin = pUsername AND UserPass = pUserPass);
SET @vSubNo = (SELECT max(SubNo) from subscription natural join user_sub where UserNo = @vUserNo and Now() between subscription.StartTime and subscription.EndTime);
if (@vSubNo is NOT NULL) then
	SELECT "DONE" as "Status" , "0" as "ErrType", "get_user_sub" as "Fun","This user have active subscription" as "Info", @vSubNo as "SubNo";
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "get_user_sub" as "Fun", "This user has no active subscription or user incorrect" as "Info";
end if;

end$$

CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `new_user` (`newLogin` VARCHAR(50), `newPass` VARCHAR(50), `newPermType` ENUM('admin','user'), `newName` VARCHAR(50), `newSurname` VARCHAR(50), `newPhone` INT(9), `newEmail` VARCHAR(50))  begin
SET @n1 = (SELECT max(UserNo) from userparking) + 1; SET @n2 = (SELECT UserNo from userparking where UserLogin = newLogin);
if (@n2 is NULL) then
	INSERT INTO userparking(UserNo, UserLogin, UserPass , PermType, Name, Surname, Phone, Email) VALUES(@n1, newLogin, newPass, newPermType, newName, newSurname, newPhone, newEmail );
	SELECT "DONE" as "Status" , "0" as "ErrType", "new_user" as "Fun","User added correctly" as "Info";
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "new_user" as "Fun", "This login is in use. User hasn't been added" as "Info";
end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pay_ticket` (IN `vTicketNo` INT, IN `vPaymentType` ENUM('cash','subscription'), IN `vSubNo` INT)  begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
SET @vPaymentTime = (SELECT PaymentTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	if (@vPaymentTime is NULL) then
		SET @vNow = Now();
		SET @vControlCode = (SELECT ROUND(((99 - 10 -1) * RAND() + 10), 0)); 	if(vPaymentType = 'cash') then
			UPDATE ticket SET PaymentType='cash' WHERE ticket.TicketNo = vTicketNo;
			UPDATE ticket SET PaymentTime=@vNow WHERE ticket.TicketNo = vTicketNo;
			UPDATE ticket SET ControlCode=@vControlCode WHERE ticket.TicketNo = vTicketNo;
			SELECT "DONE" as "Status" , "0" as "ErrType", "pay_ticket" as "Fun","Ticket charge added correctly" as "Info", @vNow as "PaymentTime", vPaymentType as "PaymentType", @vControlCode as "ControlCode";
		elseif(vPaymentType = 'subscription') then
			SET @vUserNo = (SELECT UserNo from user_sub where user_sub.SubNo = vSubNo);
			if(@vUserNo is NOT NULL) then
			
									
				INSERT INTO user_ticket(TicketNo, UserNo) VALUES(vTicketNo, @vUserNo);
				UPDATE ticket SET PaymentType='subscription' WHERE ticket.TicketNo = vTicketNo;
				UPDATE ticket SET PaymentTime=@vNow WHERE ticket.TicketNo = vTicketNo;
				UPDATE ticket SET ControlCode=@vControlCode WHERE ticket.TicketNo = vTicketNo;
				
				SELECT "DONE" as "Status" , "0" as "ErrType", "pay_ticket" as "Fun","Ticket charge added correctly" as "Info", @vNow as "PaymentTime", vPaymentType as "PaymentType", @vControlCode as "ControlCode";
			else
				SELECT "ERROR" as "Status", "1" as "ErrType", "pay_ticket" as "Fun", "This SubNo is not correct. Ticket hasn't been paid" as "Info";
			end if;
		else
			SELECT "ERROR" as "Status", "1" as "ErrType", "pay_ticket" as "Fun", "This PaymentType is not correct. Ticket hasn't been paid" as "Info";
		end if;
	else 
		SELECT "ERROR" as "Status", "1" as "ErrType", "pay_ticket" as "Fun", "Ticket has beed already paid" as "Info";
	end if;
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "pay_ticket" as "Fun", "This TicketNo is not correct. Ticket hasn't been paid" as "Info";
end if;
end$$

CREATE DEFINER=`javaparking`@`localhost` PROCEDURE `set_ticket_charge` (IN `vTicketNo` INT)  begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	SET @vNow = Now();
	SET @vDuration = (SELECT ((HOUR(TIMEDIFF(@vNow, @vEntryTime)) + 1)));
	SET @vPriceHour = (SELECT prices.Price from prices where prices.Name = 'hour1');
	
		
	if( @vPriceHour is NOT NULL) then
		SET @vCharge = @vDuration * @vPriceHour;
		UPDATE ticket SET Charge=@vCharge WHERE ticket.TicketNo = vTicketNo;
		SELECT "DONE" as "Status" , "0" as "ErrType", "set_ticket_charge" as "Fun","Ticket charge added correctly" as "Info", @vCharge as "TicketCharge", @vDuration as "DurationTime";
	else
		SELECT "ERROR" as "Status", "1" as "ErrType", "set_ticket_charge" as "Fun", "There is no price hour1 in table price. Charge hasn't been added" as "Info";	
	end if;
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "set_ticket_charge" as "Fun", "This TicketNo is not correct. Charge hasn't been added" as "Info";
end if;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `prices`
--

CREATE TABLE `prices` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Price` int(11) NOT NULL,
  `Duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `prices`
--

INSERT INTO `prices` (`Id`, `Name`, `Type`, `Price`, `Duration`) VALUES
(1, 'hour1', 'hours', 300, 1),
(2, '30days', 'subscription', 5001, 31),
(3, '90days', 'subscription', 12, 90),
(4, '180days', 'subscription', 22000, 180),
(5, '1year', 'subscription', 40000, 366);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `subscription`
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
-- Zrzut danych tabeli `subscription`
--

INSERT INTO `subscription` (`SubNo`, `StartTime`, `EndTime`, `PurchaseTime`, `Type`, `Price`) VALUES
(1, '0001-01-01 00:00:00', '9999-12-31 23:59:59', '2017-01-01 00:00:00', 'unlimited', 0),
(2, '0001-01-01 00:00:00', '9999-12-31 23:59:59', '2017-01-01 00:00:00', 'unlimited', 0),
(3, '0001-00-00 00:00:00', '9999-12-31 23:59:59', '2017-12-19 19:12:08', 'unlimited', 0),
(4, '2017-12-19 19:17:04', '2018-01-19 00:00:00', '2017-12-19 19:17:04', '30days', 5001),
(5, '2017-12-19 19:21:04', '2018-01-19 00:00:00', '2017-12-19 19:21:04', '30days', 5001),
(6, '2017-12-19 21:27:27', '9999-12-31 23:59:59', '2017-12-19 21:27:27', 'unlimited', 0),
(7, '2017-12-19 22:19:15', '2018-01-19 00:00:00', '2017-12-19 22:19:15', '30days', 5001),
(8, '2017-12-19 22:23:00', '2018-01-19 00:00:00', '2017-12-19 22:23:00', '30days', 5001),
(9, '2017-12-19 22:24:20', '2018-01-19 00:00:00', '2017-12-19 22:24:20', '30days', 5001),
(10, '2017-12-20 10:55:20', '2018-01-20 00:00:00', '2017-12-20 10:55:20', '30days', 5001),
(11, '2017-12-20 11:25:53', '2018-01-20 00:00:00', '2017-12-20 11:25:53', '30days', 5001),
(12, '2017-12-20 11:29:30', '2018-01-20 00:00:00', '2017-12-20 11:29:30', '30days', 5001),
(13, '2017-12-20 12:16:06', '2018-01-20 00:00:00', '2017-12-20 12:16:06', '30days', 5001),
(14, '2017-12-20 12:16:57', '2018-01-20 00:00:00', '2017-12-20 12:16:57', '30days', 5001),
(15, '2017-12-20 12:19:23', '2018-03-21 00:00:00', '2017-12-20 12:19:23', '90days', 12),
(16, '2017-12-20 12:20:53', '2018-03-21 00:00:00', '2017-12-20 12:20:53', '90days', 12),
(17, '2017-12-20 12:21:37', '2018-03-21 00:00:00', '2017-12-20 12:21:37', '90days', 12),
(18, '2017-12-20 12:22:21', '2018-03-21 00:00:00', '2017-12-20 12:22:21', '90days', 12),
(19, '2017-12-20 12:24:55', '2018-01-20 00:00:00', '2017-12-20 12:24:55', '30days', 5001),
(20, '2017-12-20 12:29:38', '2018-03-21 00:00:00', '2017-12-20 12:29:38', '90days', 12),
(21, '2017-12-20 12:34:38', '2018-03-21 00:00:00', '2017-12-20 12:34:38', '90days', 12),
(22, '2017-12-20 12:35:10', '2018-01-20 00:00:00', '2017-12-20 12:35:10', '30days', 5001),
(23, '2017-12-20 12:38:36', '2018-03-21 00:00:00', '2017-12-20 12:38:36', '90days', 12),
(24, '2017-12-20 12:41:49', '2018-03-21 00:00:00', '2017-12-20 12:41:49', '90days', 12),
(25, '2017-12-20 12:42:25', '2018-01-20 00:00:00', '2017-12-20 12:42:25', '30days', 5001),
(26, '2017-12-20 12:46:42', '2018-03-21 00:00:00', '2017-12-20 12:46:42', '90days', 12),
(27, '2017-12-20 12:47:39', '2018-03-21 00:00:00', '2017-12-20 12:47:39', '90days', 12),
(28, '2017-12-20 12:47:54', '2018-03-21 00:00:00', '2017-12-20 12:47:54', '90days', 12),
(29, '2017-12-20 12:48:59', '2018-03-21 00:00:00', '2017-12-20 12:48:59', '90days', 12),
(30, '2017-12-20 13:06:46', '2018-01-20 00:00:00', '2017-12-20 13:06:46', '30days', 5001),
(31, '2017-12-20 13:09:31', '2018-03-21 00:00:00', '2017-12-20 13:09:31', '90days', 12),
(32, '2017-12-20 13:10:36', '2018-01-20 00:00:00', '2017-12-20 13:10:36', '30days', 5001),
(33, '2017-12-20 13:12:13', '2018-03-21 00:00:00', '2017-12-20 13:12:13', '90days', 12),
(34, '2017-12-20 13:13:01', '2018-03-21 00:00:00', '2017-12-20 13:13:01', '90days', 12),
(35, '2017-12-20 13:38:43', '2018-03-21 00:00:00', '2017-12-20 13:38:43', '90days', 12),
(36, '2017-12-20 13:40:19', '2018-03-21 00:00:00', '2017-12-20 13:40:19', '90days', 12),
(37, '2017-12-20 13:44:28', '2018-03-21 00:00:00', '2017-12-20 13:44:28', '90days', 12),
(38, '2017-12-20 13:47:33', '2018-03-21 00:00:00', '2017-12-20 13:47:33', '90days', 12),
(39, '2017-12-20 13:54:35', '2018-06-19 00:00:00', '2017-12-20 13:54:35', '180days', 22000),
(40, '2017-12-20 13:55:34', '2018-03-21 00:00:00', '2017-12-20 13:55:34', '90days', 12),
(41, '2017-12-20 13:56:05', '2018-03-21 00:00:00', '2017-12-20 13:56:05', '90days', 12),
(42, '2017-12-20 13:56:38', '2018-03-21 00:00:00', '2017-12-20 13:56:38', '90days', 12),
(43, '2017-12-20 13:56:51', '2018-03-21 00:00:00', '2017-12-20 13:56:51', '90days', 12),
(44, '2017-12-20 14:46:21', '2018-01-20 00:00:00', '2017-12-20 14:46:21', '30days', 5001),
(45, '2017-12-28 16:20:52', '2018-01-28 00:00:00', '2017-12-28 16:20:52', '30days', 5001),
(46, '2018-01-11 18:02:34', '2018-02-11 00:00:00', '2018-01-11 18:02:34', '30days', 5001);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ticket`
--

CREATE TABLE `ticket` (
  `TicketNo` int(11) NOT NULL,
  `EntryTime` datetime NOT NULL,
  `LeaveTime` datetime DEFAULT NULL,
  `PaymentTime` datetime DEFAULT NULL,
  `PaymentType` enum('cash','subscription') COLLATE utf8_unicode_ci DEFAULT NULL,
  `Charge` int(11) DEFAULT NULL,
  `ControlCode` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `ticket`
--

INSERT INTO `ticket` (`TicketNo`, `EntryTime`, `LeaveTime`, `PaymentTime`, `PaymentType`, `Charge`, `ControlCode`) VALUES
(1, '2017-11-15 09:00:00', NULL, '2017-11-16 13:43:05', 'cash', 251700, 67),
(2, '2017-11-16 01:02:51', NULL, '2017-11-16 14:17:35', 'cash', 3900, 14),
(3, '2017-11-21 18:00:45', '2017-11-22 00:50:04', '2017-11-22 13:47:21', 'cash', NULL, 74),
(4, '2017-11-21 22:41:07', '2017-11-22 01:13:18', '2017-11-22 13:49:07', 'cash', NULL, 32),
(5, '2017-11-21 22:41:08', '2017-11-22 01:16:50', '2017-11-22 08:00:00', NULL, NULL, NULL),
(6, '2017-11-21 23:21:19', '2017-11-22 01:18:33', '2017-11-22 19:00:00', NULL, NULL, 12),
(7, '2017-11-21 23:21:20', NULL, '2017-11-22 23:00:00', NULL, NULL, 10),
(8, '2017-11-22 19:47:14', NULL, '2017-11-23 23:00:00', NULL, NULL, 0),
(9, '2017-11-22 19:48:26', NULL, '2017-12-28 18:09:55', 'cash', 251700, 34),
(10, '2017-11-22 19:48:32', NULL, '2017-12-28 19:19:36', 'cash', 251700, 78),
(11, '2017-11-22 19:49:00', NULL, NULL, NULL, NULL, NULL),
(12, '2017-11-22 19:50:04', NULL, NULL, NULL, NULL, NULL),
(13, '2017-11-22 19:54:52', NULL, '2018-01-11 20:18:15', 'subscription', NULL, 50),
(14, '2017-11-22 19:55:46', NULL, NULL, NULL, NULL, NULL),
(15, '2017-11-22 19:57:02', NULL, NULL, NULL, NULL, NULL),
(16, '2017-11-22 19:57:42', NULL, NULL, NULL, NULL, NULL),
(17, '2017-11-22 20:09:21', NULL, NULL, NULL, NULL, NULL),
(18, '2017-11-22 20:12:52', NULL, NULL, NULL, NULL, NULL),
(19, '2017-11-22 20:34:11', NULL, NULL, NULL, NULL, NULL),
(20, '2017-11-22 20:35:13', NULL, NULL, NULL, NULL, NULL),
(21, '2017-11-22 20:39:16', NULL, NULL, NULL, NULL, NULL),
(22, '2017-11-22 20:40:32', NULL, '2017-11-30 00:00:00', 'cash', NULL, 33),
(23, '2017-11-22 20:42:40', NULL, NULL, NULL, NULL, NULL),
(24, '2017-11-22 20:45:56', NULL, NULL, NULL, NULL, NULL),
(25, '2017-11-22 20:46:49', NULL, NULL, NULL, 251700, NULL),
(26, '2017-11-22 20:47:32', NULL, NULL, NULL, NULL, NULL),
(27, '2017-11-22 20:50:28', NULL, NULL, NULL, NULL, NULL),
(28, '2017-11-22 20:51:01', NULL, NULL, NULL, NULL, NULL),
(29, '2017-11-22 20:52:22', NULL, NULL, NULL, NULL, NULL),
(30, '2017-11-22 20:53:11', NULL, NULL, NULL, NULL, NULL),
(31, '2017-11-22 20:54:18', NULL, NULL, NULL, NULL, NULL),
(32, '2017-11-22 21:08:18', NULL, NULL, NULL, NULL, NULL),
(33, '2017-11-22 21:09:06', NULL, NULL, NULL, NULL, NULL),
(34, '2017-11-22 21:09:56', NULL, NULL, NULL, NULL, NULL),
(35, '2017-11-22 21:10:42', NULL, NULL, NULL, NULL, NULL),
(36, '2017-11-22 21:11:22', NULL, NULL, NULL, NULL, NULL),
(37, '2017-11-22 21:11:57', NULL, NULL, NULL, NULL, NULL),
(38, '2017-11-22 21:12:43', NULL, NULL, NULL, NULL, NULL),
(39, '2017-11-22 21:18:05', NULL, NULL, NULL, NULL, NULL),
(40, '2017-11-22 21:18:58', NULL, NULL, NULL, NULL, NULL),
(41, '2017-11-22 21:20:05', NULL, NULL, NULL, NULL, NULL),
(42, '2017-11-22 21:21:47', NULL, NULL, NULL, NULL, NULL),
(43, '2017-11-22 21:22:56', NULL, NULL, NULL, NULL, NULL),
(44, '2017-11-22 21:23:40', NULL, NULL, NULL, NULL, NULL),
(45, '2017-11-22 21:24:43', NULL, NULL, NULL, NULL, NULL),
(46, '2017-11-22 21:29:39', NULL, NULL, NULL, NULL, NULL),
(47, '2017-11-22 21:30:35', NULL, NULL, NULL, NULL, NULL),
(48, '2017-11-22 21:31:11', NULL, NULL, NULL, NULL, NULL),
(49, '2017-11-22 21:36:35', NULL, NULL, NULL, NULL, NULL),
(50, '2017-11-22 21:49:15', NULL, '2018-01-11 20:34:25', 'subscription', NULL, 68),
(51, '2017-11-22 22:08:22', NULL, NULL, NULL, NULL, NULL),
(52, '2017-11-22 22:18:08', NULL, NULL, NULL, NULL, NULL),
(53, '2017-11-22 22:19:01', NULL, NULL, NULL, NULL, NULL),
(54, '2017-11-22 22:31:52', NULL, NULL, NULL, NULL, NULL),
(55, '2017-11-22 22:32:14', NULL, NULL, NULL, NULL, NULL),
(56, '2017-11-22 22:45:21', NULL, NULL, NULL, NULL, NULL),
(57, '2017-11-22 22:45:30', NULL, NULL, NULL, NULL, NULL),
(58, '2017-11-22 22:47:02', NULL, NULL, NULL, NULL, NULL),
(59, '2017-11-22 22:48:21', NULL, NULL, NULL, NULL, NULL),
(60, '2017-11-22 22:48:27', NULL, NULL, NULL, NULL, NULL),
(61, '2017-11-22 22:48:38', NULL, NULL, NULL, NULL, NULL),
(62, '2017-11-22 22:48:40', NULL, NULL, NULL, NULL, NULL),
(63, '2017-11-22 22:50:06', NULL, NULL, NULL, NULL, NULL),
(64, '2017-11-22 22:52:26', NULL, NULL, NULL, NULL, NULL),
(65, '2017-11-22 22:52:37', NULL, NULL, NULL, NULL, NULL),
(66, '2017-11-22 22:53:20', NULL, NULL, NULL, NULL, NULL),
(67, '2017-11-22 22:53:49', NULL, NULL, NULL, NULL, NULL),
(68, '2017-11-22 22:59:21', NULL, NULL, NULL, NULL, NULL),
(69, '2017-11-22 22:59:32', NULL, NULL, NULL, NULL, NULL),
(70, '2017-11-22 22:59:37', NULL, NULL, NULL, NULL, NULL),
(71, '2017-11-22 23:12:32', NULL, NULL, NULL, NULL, NULL),
(72, '2017-11-22 23:12:50', NULL, NULL, NULL, NULL, NULL),
(73, '2017-11-22 23:14:17', NULL, NULL, NULL, NULL, NULL),
(74, '2017-11-22 23:14:28', NULL, NULL, NULL, NULL, NULL),
(75, '2017-11-23 00:58:38', NULL, NULL, NULL, NULL, NULL),
(76, '2017-11-23 01:01:21', NULL, NULL, NULL, NULL, NULL),
(77, '2017-11-23 01:01:57', NULL, NULL, NULL, NULL, NULL),
(78, '2017-11-23 12:07:52', NULL, NULL, NULL, NULL, NULL),
(79, '2017-11-23 12:08:31', NULL, NULL, NULL, NULL, NULL),
(80, '2017-11-23 12:09:13', NULL, NULL, NULL, NULL, NULL),
(81, '2017-11-23 12:11:53', NULL, NULL, NULL, NULL, NULL),
(82, '2017-11-23 12:12:04', NULL, NULL, NULL, NULL, NULL),
(83, '2017-11-23 12:12:13', NULL, NULL, NULL, NULL, NULL),
(84, '2017-11-23 12:12:17', NULL, NULL, NULL, NULL, NULL),
(85, '2017-11-23 12:12:22', NULL, NULL, NULL, NULL, NULL),
(86, '2017-11-23 12:12:25', NULL, NULL, NULL, NULL, NULL),
(87, '2017-11-23 12:12:49', NULL, NULL, NULL, NULL, NULL),
(88, '2017-11-23 12:12:53', NULL, NULL, NULL, NULL, NULL),
(89, '2017-11-23 12:12:56', NULL, NULL, NULL, NULL, NULL),
(90, '2017-11-23 12:13:00', NULL, NULL, NULL, NULL, NULL),
(91, '2017-11-23 12:14:24', NULL, NULL, NULL, NULL, NULL),
(92, '2017-11-23 12:14:51', NULL, NULL, NULL, NULL, NULL),
(93, '2017-11-23 12:16:09', NULL, NULL, NULL, NULL, NULL),
(94, '2017-11-23 12:16:25', NULL, NULL, NULL, NULL, NULL),
(95, '2017-11-23 12:17:13', NULL, NULL, NULL, NULL, NULL),
(96, '2017-11-23 12:17:55', NULL, NULL, NULL, NULL, NULL),
(97, '2017-11-23 12:20:00', NULL, NULL, NULL, NULL, NULL),
(98, '2017-11-23 12:20:07', NULL, NULL, NULL, NULL, NULL),
(99, '2017-11-23 12:20:54', NULL, NULL, NULL, NULL, NULL),
(100, '2017-11-23 12:21:51', NULL, NULL, NULL, NULL, NULL),
(101, '2017-11-23 12:22:13', NULL, NULL, NULL, NULL, NULL),
(102, '2017-11-23 12:23:24', NULL, NULL, NULL, NULL, NULL),
(103, '2017-11-23 12:23:37', NULL, NULL, NULL, NULL, NULL),
(104, '2017-11-23 12:26:16', NULL, NULL, NULL, NULL, NULL),
(105, '2017-11-23 12:28:43', NULL, NULL, NULL, NULL, NULL),
(106, '2017-11-23 12:33:41', NULL, NULL, NULL, NULL, NULL),
(107, '2017-11-23 12:35:34', NULL, NULL, NULL, NULL, NULL),
(108, '2017-11-23 12:38:14', NULL, NULL, NULL, NULL, NULL),
(109, '2017-11-23 12:38:53', NULL, NULL, NULL, NULL, NULL),
(110, '2017-11-23 12:44:52', NULL, NULL, NULL, NULL, NULL),
(111, '2017-11-23 12:45:34', NULL, NULL, NULL, NULL, NULL),
(112, '2017-11-23 12:48:57', NULL, NULL, NULL, NULL, NULL),
(113, '2017-11-23 12:51:05', NULL, NULL, NULL, NULL, NULL),
(114, '2017-11-23 12:51:24', NULL, NULL, NULL, NULL, NULL),
(115, '2017-11-23 12:53:50', NULL, NULL, NULL, NULL, NULL),
(116, '2017-11-23 12:54:05', NULL, NULL, NULL, NULL, NULL),
(117, '2017-11-23 12:54:46', NULL, NULL, NULL, NULL, NULL),
(118, '2017-11-23 13:01:38', NULL, NULL, NULL, NULL, NULL),
(119, '2017-11-23 13:03:04', NULL, NULL, NULL, NULL, NULL),
(120, '2017-11-23 13:03:21', NULL, NULL, NULL, NULL, NULL),
(121, '2017-11-23 13:04:55', NULL, NULL, NULL, NULL, NULL),
(122, '2017-11-23 13:05:07', NULL, NULL, NULL, NULL, NULL),
(123, '2017-11-23 13:07:10', NULL, NULL, NULL, NULL, NULL),
(124, '2017-11-23 13:08:25', NULL, NULL, NULL, NULL, NULL),
(125, '2017-11-23 13:24:44', NULL, NULL, NULL, NULL, NULL),
(126, '2017-11-23 13:25:04', NULL, NULL, NULL, NULL, NULL),
(127, '2017-11-24 21:13:19', NULL, NULL, NULL, NULL, NULL),
(128, '2017-11-24 21:16:49', NULL, NULL, NULL, NULL, NULL),
(129, '2017-11-24 21:18:14', NULL, NULL, NULL, NULL, NULL),
(130, '2017-11-24 21:20:30', NULL, NULL, NULL, NULL, NULL),
(131, '2017-11-27 16:44:52', NULL, NULL, NULL, NULL, NULL),
(132, '2017-11-27 17:00:52', NULL, NULL, NULL, NULL, NULL),
(133, '2017-12-20 11:27:45', NULL, '2017-12-28 20:24:56', 'subscription', NULL, 35),
(134, '2017-12-28 17:57:24', NULL, '2017-12-28 20:18:22', 'subscription', 101100, 64),
(135, '2018-01-11 17:59:14', NULL, '2018-01-11 18:00:27', 'cash', 300, 46),
(136, '2018-01-11 18:00:55', NULL, '2018-01-11 18:03:56', 'subscription', NULL, 29),
(137, '2018-01-11 20:15:35', NULL, '2018-01-11 20:18:17', 'subscription', NULL, 72),
(138, '2018-01-11 20:15:38', NULL, '2018-01-11 20:18:29', 'subscription', NULL, 93),
(139, '2018-01-11 20:28:28', NULL, '2018-01-11 20:34:07', 'subscription', NULL, 29);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `userparking`
--

CREATE TABLE `userparking` (
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
-- Zrzut danych tabeli `userparking`
--

INSERT INTO `userparking` (`UserNo`, `UserLogin`, `UserPass`, `PermType`, `Name`, `Surname`, `Phone`, `Email`) VALUES
(1, 'admin', 'admin', 'admin', 'Admin', 'Admin', 660770880, 'admin@sql.com'),
(2, 'javaparking', 'javaparking', 'admin', 'Java', 'Park', 999999999, 'Java@Park.com'),
(3, 'Klos', 'Labs', 'user', 'Klos', 'Labs', 111222333, 'vip@kk'),
(4, 'user', 'user', 'user', NULL, NULL, NULL, NULL),
(5, '11', '11', 'user', '11', '11', 11, '11'),
(6, 'nowy', 'nowy', 'user', 'aaa', 'bbb', 12344, 'zds@ewdw.com'),
(7, 'nowy2', 'nowy2', 'user', 'aa', 'vv', 323, 'dwdad@dsz.com'),
(8, 'aaa', 'aaa', 'user', 'aaa', 'aaa', 22, 'a#a'),
(9, 'bbb', 'bbb', 'user', 'bbb', 'bbb', 233, 'bb@bb.com'),
(10, '1', '3', 'admin', '2', '1', 2, '1'),
(11, 'ccc', 'cc', 'user', 'cc', 'cc', 22, 'cc'),
(12, 'dd', 'dd', 'user', 'dd', 'dd', 22, 'dd'),
(13, '2222', '22', 'user', '22', '2', 22, '2'),
(14, 'aaaaaaaa', 'a', 'admin', 'aaa', 'a', 22, '33a'),
(15, '2232323', '2323', 'user', '23232', '3232', 32323, '32'),
(16, 'loginklienta', 'hasloklienta', 'user', 'Imie', 'Nazwisko', 123456789, 'email@email.com');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_sub`
--

CREATE TABLE `user_sub` (
  `UserNo` int(11) NOT NULL,
  `SubNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `user_sub`
--

INSERT INTO `user_sub` (`UserNo`, `SubNo`) VALUES
(1, 1),
(2, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(4, 12),
(4, 13),
(4, 14),
(4, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(4, 21),
(4, 22),
(4, 23),
(4, 24),
(4, 25),
(4, 26),
(4, 27),
(4, 28),
(4, 29),
(4, 30),
(4, 31),
(4, 32),
(4, 33),
(4, 34),
(4, 35),
(4, 36),
(4, 37),
(4, 38),
(4, 39),
(4, 40),
(4, 41),
(4, 42),
(4, 43),
(4, 44),
(4, 45),
(5, 11),
(16, 46);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_ticket`
--

CREATE TABLE `user_ticket` (
  `UserNo` int(11) NOT NULL,
  `TicketNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `user_ticket`
--

INSERT INTO `user_ticket` (`UserNo`, `TicketNo`) VALUES
(1, 2),
(1, 133),
(1, 134),
(4, 13),
(4, 50),
(4, 137),
(4, 138),
(4, 139),
(16, 136);

--
-- Indeksy dla zrzutów tabel
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
-- Indexes for table `userparking`
--
ALTER TABLE `userparking`
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
-- AUTO_INCREMENT dla tabeli `prices`
--
ALTER TABLE `prices`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT dla tabeli `subscription`
--
ALTER TABLE `subscription`
  MODIFY `SubNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT dla tabeli `ticket`
--
ALTER TABLE `ticket`
  MODIFY `TicketNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=140;
--
-- AUTO_INCREMENT dla tabeli `userparking`
--
ALTER TABLE `userparking`
  MODIFY `UserNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `user_sub`
--
ALTER TABLE `user_sub`
  ADD CONSTRAINT `user_sub_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `userparking` (`UserNo`),
  ADD CONSTRAINT `user_sub_fk_2` FOREIGN KEY (`SubNo`) REFERENCES `subscription` (`SubNo`);

--
-- Ograniczenia dla tabeli `user_ticket`
--
ALTER TABLE `user_ticket`
  ADD CONSTRAINT `user_ticket_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `userparking` (`UserNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_ticket_fk_2` FOREIGN KEY (`TicketNo`) REFERENCES `ticket` (`TicketNo`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
