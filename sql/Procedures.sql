#=========================================================================================================
#=========================================== TABELE (TEMPORARY) ===================================================
#=========================================================================================================
CREATE TABLE `CarPark`.`Ticket` ( `TicketNo` INT NOT NULL AUTO_INCREMENT , `EntryTime` DATE NOT NULL , `LeaveTime` DATE NULL DEFAULT NULL , `PaymentTime` DATE NULL DEFAULT NULL , `PaymentType` ENUM('cash','subscription') NULL DEFAULT NULL , `Charge` INT NULL DEFAULT NULL , PRIMARY KEY (`TicketNo`)) ENGINE = InnoDB;

CREATE TABLE `carpark`.`customer` ( `CustNo` INT NOT NULL AUTO_INCREMENT , `Name` VARCHAR(50) NOT NULL , `Surname` VARCHAR(50) NOT NULL , `Login` VARCHAR(50) NOT NULL , `Password` VARCHAR(50) NOT NULL , `Phone` INT(9) NULL , `Email` VARCHAR(50) NULL , PRIMARY KEY (`CustNo`)) ENGINE = InnoDB;

CREATE TABLE `carpark`.`Subscription` ( `SubNo` INT NOT NULL AUTO_INCREMENT , `StartTime` DATE NOT NULL , `EndTime` DATE NOT NULL , `PurchaseTime` DATE NOT NULL , `Typ` ENUM('30days','90days','180days','1year','unlimited') NOT NULL , PRIMARY KEY (`SubNo`)) ENGINE = InnoDB;


ALTER TABLE `user_ticket` ADD CONSTRAINT `user_ticket_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_ticket` ADD CONSTRAINT `user_ticket_fk_2` FOREIGN KEY (`TicketNo`) REFERENCES `ticket` (`TicketNo`);

ALTER TABLE `user_sub` ADD CONSTRAINT `user_sub_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`);

ALTER TABLE `user_sub` ADD CONSTRAINT `user_sub_fk_2` FOREIGN KEY (`SubNo`) REFERENCES `subscription` (`SubNo`);


#=========================================================================================================
#=========================================== PROCEDURY ===================================================
#=========================================================================================================



#=========================================================================================================
# procedura dodająca nowego usera       		 ========= new_user
#=========================================================================================================
DELIMITER //
CREATE PROCEDURE new_user(newLogin varchar(50), newPass varchar(50), newPermType enum('admin', 'user'), newName varchar(50), newSurname varchar(50), newPhone	int(9), newEmail varchar(50))
begin
SET @n1 = (SELECT max(UserNo) from userparking) + 1; #Search new user number (UserNo)
SET @n2 = (SELECT UserNo from userparking where UserLogin = newLogin);
if (@n2 is NULL) then
	INSERT INTO userparking(UserNo, UserLogin, UserPass , PermType, Name, Surname, Phone, Email) VALUES(@n1, newLogin, newPass, newPermType, newName, newSurname, newPhone, newEmail );
	SELECT "DONE" as "Status" , "0" as "ErrType", "new_user" as "Fun","User added correctly" as "Info";
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "new_user" as "Fun", "This login is in use. User hasn't been added" as "Info";
end if;
end
//
DELIMITER ;

#====================
CALL new_user("Klos", "Labs", "user", "Klos", "Labs", "111222333", "vip@kk");
#====================

#=========================================================================================================
# procedura wykupywania abonamentu        		 ========= buy_sub
#=========================================================================================================
DELIMITER //
CREATE PROCEDURE buy_sub(vLogin varchar(50), vType enum('30days','90days','180days','1year','unlimited'), vStartTime datetime)
begin
SET @vUserNo = (SELECT UserNo from userparking where UserLogin = vLogin);
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
			SELECT "DONE" as "Status" , "0" as "ErrType", "buy_sub" as "Fun","Subscription added correctly" as "Info";
		end if;
	else
		SELECT "ERROR" as "Status", "1" as "ErrType", "buy_sub" as "Fun", "Incorrect type of subscription. Subscription hasn't been added" as "Info";
	end if;
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "buy_sub" as "Fun", "This login is not correct. Subscription hasn't been added" as "Info";
end if;
end
//
DELIMITER ;

#====================
CALL buy_sub("Klos", '90days', "2017-11-16 12:30:30");
#====================

#=========================================================================================================
# procedura pobrania biletu       		 ========= get_ticket
#=========================================================================================================
DELIMITER //
CREATE PROCEDURE get_ticket()
begin
SET @vTicNo = (SELECT max(TicketNo) from ticket) + 1; #Search new ticket number (TicketNo)
SET @vEntryTime = NOW();
	
INSERT INTO ticket(TicketNo, EntryTime) VALUES(@vTicNo, @vEntryTime);
SELECT "DONE" as "Status" , "0" as "ErrType", "get_ticket" as "Fun","New Ticket added correctly" as "Info", @vTicNo as "TicketNo", @vEntryTime as "EntryTime";

end
//
DELIMITER ;

#====================
CALL get_ticket();
#====================

#=========================================================================================================
# procedura ustalenia ceny biletu      		 ========= set_ticket_charge
#=========================================================================================================
DELIMITER //
CREATE OR REPLACE PROCEDURE set_ticket_charge(vTicketNo int)
begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
SET @vPaymentTime = (SELECT PaymentTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	if (@vPaymentTime is NULL) then
		SET @vNow = Now();
		SET @vDuration = (SELECT ((HOUR(TIMEDIFF(@vNow, @vEntryTime)) + 1)));
		SET @vPriceHour = (SELECT prices.Price from prices where prices.Name = 'hour1');
		
		#TODO Check if ticket was paid to avoid paying again
		
		if( @vPriceHour is NOT NULL) then
			SET @vCharge = @vDuration * @vPriceHour;
			UPDATE ticket SET Charge=@vCharge WHERE ticket.TicketNo = vTicketNo;
			SELECT "DONE" as "Status" , "0" as "ErrType", "set_ticket_charge" as "Fun","Ticket charge added correctly" as "Info", @vCharge as "TicketCharge", @vDuration as "DurationTime";
		else
			SELECT "ERROR" as "Status", "1" as "ErrType", "set_ticket_charge" as "Fun", "There is no price hour1 in table price. Charge hasn't been added" as "Info";	
		end if;
	else
		SELECT "ERROR" as "Status", "1" as "ErrType", "set_ticket_charge" as "Fun", "Ticket has beed already paid" as "Info";	
	end if;
		
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "set_ticket_charge" as "Fun", "This TicketNo is not correct. Charge hasn't been added" as "Info";
end if;
end
//
DELIMITER ;

#====================
CALL set_ticket_charge(1);
#====================

#=========================================================================================================
# procedura ustawienia płatności za bilet    		 ========= pay_ticket			 # pay for ticket	vSubNo int can be just 0 if there is no subscription payment type
#=========================================================================================================
DELIMITER //
CREATE OR REPLACE PROCEDURE `pay_ticket`(IN `vTicketNo` INT, IN `vPaymentType` ENUM('cash','subscription'), IN `vSubNo` INT) NOT DETERMINISTIC CONTAINS SQL SQL SECURITY DEFINER begin
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
end
//
DELIMITER ;

#====================
CALL pay_ticket(2, 'cash', 0);
#====================

#=========================================================================================================
# check if user have active subscription    		 ========= get_user_sub			
#=========================================================================================================
DELIMITER //
CREATE OR REPLACE PROCEDURE get_user_sub(pUserName varchar(255), pUserPass varchar(255))
begin
SET @vNow = Now();
SET @vUserNo = (SELECT UserNo FROM userparking WHERE UserLogin = pUsername AND UserPass = pUserPass);
SET @vSubNo = (SELECT max(SubNo) from subscription natural join user_sub where UserNo = @vUserNo and Now() between subscription.StartTime and subscription.EndTime);
if (@vSubNo is NOT NULL) then
	SELECT "DONE" as "Status" , "0" as "ErrType", "get_user_sub" as "Fun","This user have active subscription" as "Info", @vSubNo as "SubNo";
else
	SELECT "ERROR" as "Status", "1" as "ErrType", "get_user_sub" as "Fun", "This user has no active subscription or user incorrect" as "Info";
end if;

end
//
DELIMITER ;

#====================
CALL get_user_sub(2);
#====================

#=========================================================================================================
# get cebulaki 	sprawdź ile zarobiłeś			 ========= get_money		
#=========================================================================================================
DELIMITER //
CREATE PROCEDURE get_money(vDateFrom datetime, vDateTo datetime)
begin
SET @vGetMyMoneyTic = (SELECT sum(Charge) as 'MyMoney' from ticket where ticket.PaymentType = 'cash' and ticket.PaymentTime is NOT NULL and ticket.PaymentTime between vDateFrom and vDateTo);
SET @vGetMyMoneySub = (SELECT sum(Price) as 'MyMoney' from subscription where subscription.PurchaseTime between vDateFrom and vDateTo);
	SELECT "DONE" as "Status" , "0" as "ErrType", "get_user_sub" as "Fun","YourMoney" as "Info", @vGetMyMoneyTic as "TicketMoney", @vGetMyMoneySub as "SubMoney", @vGetMyMoneyTic + @vGetMyMoneySub as "TotalMoney";
end
//
DELIMITER ;

#====================
CALL get_money("0001-01-01 00:00:00", "9999-12-31 23:59:59");
#====================

#=========================================================================================================
# sprawdź czy można wyjechać (15min na wyjazd)	 ========= 	check_ticket_can_exit	return 0 if user can leave parking
#=========================================================================================================
DELIMITER //
CREATE PROCEDURE check_ticket_can_exit(vTicketNo int, vControlCode int)
begin
SET @vPaymentTime = (SELECT PaymentTime from ticket where TicketNo = vTicketNo);
if (@vPaymentTime is NOT NULL) then
	SET @vControlCodeDB = (SELECT ControlCode from ticket where TicketNo = vTicketNo);
	if(vControlCode = @vControlCodeDB) then
		SET @vLeaveTime = (SELECT LeaveTime from ticket where TicketNo = vTicketNo);
		if(@vLeaveTime is NULL) then
			SET @vNow = Now();
			SET @vDuration = (SELECT (TIMEDIFF(@vNow, @vPaymentTime)));
			if(@vDuration < "00:15:00") then
				#UPDATE ticket SET LeaveTime=@vNow WHERE ticket.TicketNo = vTicketNo;
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
end
//
DELIMITER ;

#====================
CALL check_ticket_can_exit(1);
#====================

