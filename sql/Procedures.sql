CREATE TABLE `CarPark`.`Ticket` ( `TicketNo` INT NOT NULL AUTO_INCREMENT , `EntryTime` DATE NOT NULL , `LeaveTime` DATE NULL DEFAULT NULL , `PaymentTime` DATE NULL DEFAULT NULL , `PaymentType` ENUM('cash','subscription') NULL DEFAULT NULL , `Charge` INT NULL DEFAULT NULL , PRIMARY KEY (`TicketNo`)) ENGINE = InnoDB;

CREATE TABLE `carpark`.`customer` ( `CustNo` INT NOT NULL AUTO_INCREMENT , `Name` VARCHAR(50) NOT NULL , `Surname` VARCHAR(50) NOT NULL , `Login` VARCHAR(50) NOT NULL , `Password` VARCHAR(50) NOT NULL , `Phone` INT(9) NULL , `Email` VARCHAR(50) NULL , PRIMARY KEY (`CustNo`)) ENGINE = InnoDB;

CREATE TABLE `carpark`.`Subscription` ( `SubNo` INT NOT NULL AUTO_INCREMENT , `StartTime` DATE NOT NULL , `EndTime` DATE NOT NULL , `PurchaseTime` DATE NOT NULL , `Typ` ENUM('30days','90days','180days','1year','unlimited') NOT NULL , PRIMARY KEY (`SubNo`)) ENGINE = InnoDB;






ALTER TABLE `user_ticket` ADD CONSTRAINT `user_ticket_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `user_ticket` ADD CONSTRAINT `user_ticket_fk_2` FOREIGN KEY (`TicketNo`) REFERENCES `ticket` (`TicketNo`);

ALTER TABLE `user_sub` ADD CONSTRAINT `user_sub_fk_1` FOREIGN KEY (`UserNo`) REFERENCES `user` (`UserNo`);

ALTER TABLE `user_sub` ADD CONSTRAINT `user_sub_fk_2` FOREIGN KEY (`SubNo`) REFERENCES `subscription` (`SubNo`);



#procedura dodająca nowego usera

DELIMITER //
CREATE PROCEDURE new_user(newLogin varchar(50), newPass varchar(50), newPermType enum('admin', 'user'), newName varchar(50), newSurname varchar(50), newPhone	int(9), newEmail varchar(50))
begin
SET @n1 = (SELECT max(UserNo) from user) + 1; #Search new user number (UserNo)
SET @n2 = (SELECT UserNo from user where UserLogin = newLogin);
if (@n2 is NULL) then
	INSERT INTO userparking(UserNo, UserLogin, UserPass , PermType, Name, Surname, Phone, Email) VALUES(@n1, newLogin, newPass, newPermType, newName, newSurname, newPhone, newEmail );
	SELECT "DONE" as "DONE" , "0" as "ErrType", "new_user" as "Fun","User added correctly" as "Info";
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "new_user" as "Fun", "This login is in use. User hasn't been added" as "Info";
end if;
end
//
DELIMITER ;


CALL new_user("Klos", "Labs", "user", "Klos", "Labs", "111222333", "vip@kk");


# procedura wykupywania abonamentu


DELIMITER //
CREATE PROCEDURE buy_sub(vLogin varchar(50), vType enum('30days','90days','180days','1year','unlimited'), vStartTime datetime)
begin
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
end
//
DELIMITER ;

CALL buy_sub("Klos", '90days', "2017-11-16 12:30:30");

# procedura pobrania biletu

DELIMITER //
CREATE PROCEDURE get_ticket()
begin
SET @vTicNo = (SELECT max(TicketNo) from ticket) + 1; #Search new ticket number (TicketNo)
SET @vEntryTime = NOW();
	
INSERT INTO ticket(TicketNo, EntryTime) VALUES(@vTicNo, @vEntryTime);
SELECT "DONE" as "DONE" , "0" as "ErrType", "get_ticket" as "Fun","New Ticket added correctly" as "Info", @vTicNo as "TicketNo", @vEntryTime as "EntryTime";

end
//
DELIMITER ;

CALL get_ticket();


# set ticket value

DELIMITER //
CREATE PROCEDURE set_ticket_charge(vTicketNo int)
begin
SET @vEntryTime = (SELECT EntryTime from ticket where TicketNo = vTicketNo);
if (@vEntryTime is NOT NULL) then
	SET @vNow = Now();
	SET @vDuration = (SELECT ((HOUR(TIMEDIFF(@vNow, @vEntryTime)) + 1)));
	SET @vPriceHour = (SELECT prices.Price from prices where prices.Type = 'hour1');
	
	#TODO Check if ticket was paid to avoid paying again
	
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
end
//
DELIMITER ;

CALL set_ticket_charge(1);

# pay for ticket	vSubNo int can be just 0 if there is no subscription payment type

DELIMITER //
CREATE PROCEDURE pay_ticket(vTicketNo int, vPaymentType enum('cash', 'subscription'), vSubNo int)
begin
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
end
//
DELIMITER ;

CALL pay_ticket(2, 'cash', 0);


# check if user have active subscription

DELIMITER //
CREATE PROCEDURE get_user_sub(vUserNo int)
begin
SET @vNow = Now();
SET @vSubNo = (SELECT SubNo from subscription natural join user_sub where UserNo = vUserNo and Now() between subscription.StartTime and subscription.EndTime);
if (@vSubNo is NOT NULL) then
	SELECT "DONE" as "DONE" , "0" as "ErrType", "get_user_sub" as "Fun","This user have active subscription" as "Info", @vSubNo as "SubNo";
else
	SELECT "ERROR" as "ERROR", "1" as "ErrType", "get_user_sub" as "Fun", "This user has no active subscription or user incorrect" as "Info";
end if;

end
//
DELIMITER ;

CALL get_user_sub(2);



# get cebulaki

DELIMITER //
CREATE PROCEDURE get_money(vDateFrom datetime, vDateTo datetime)
begin
SET @vGetMyMoneyTic = (SELECT sum(Charge) as 'MyMoney' from ticket where ticket.PaymentType = 'cash' and ticket.PaymentTime is NOT NULL and ticket.PaymentTime between vDateFrom and vDateTo);
SET @vGetMyMoneySub = (SELECT sum(Price) as 'MyMoney' from subscription where subscription.PurchaseTime between vDateFrom and vDateTo);
	SELECT "DONE" as "DONE" , "0" as "ErrType", "get_user_sub" as "Fun","YourMoney" as "Info", @vGetMyMoneyTic as "TicketMoney", @vGetMyMoneySub as "SubMoney", @vGetMyMoneyTic + @vGetMyMoneySub as "TotalMoney";
end
//
DELIMITER ;

CALL get_money("0001-01-01 00:00:00", "9999-12-31 23:59:59");




