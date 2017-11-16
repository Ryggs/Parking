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
	INSERT INTO user(UserNo, UserLogin, UserPass , PermType, Name, Surname, Phone, Email) VALUES(@n1, newLogin, newPass, newPermType, newName, newSurname, newPhone, newEmail );
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
		SET @vEndTime = DATE_ADD(vStartTime, INTERVAL 30 DAY);
	elseif(vType = '90days') then
		SET @vEndTime = DATE_ADD(vStartTime, INTERVAL 90 DAY);
	elseif(vType = '180days')then
		SET @vEndTime = DATE_ADD(vStartTime, INTERVAL 180 DAY);		
	elseif(vType = '1year')then
		SET @vEndTime = DATE_ADD(vStartTime, INTERVAL 1 YEAR);	
	elseif(vType = 'unlimited')then
		SET @vEndTime = "9999-12-31 23:59:59";
	else
		SET @vEndTime = DATE_ADD(vStartTime, INTERVAL 30 DAY);	#else 30days
	end if;
	
	INSERT INTO subscription(SubNo, StartTime, EndTime, PurchaseTime, Type) VALUES(@vSubNo, vStartTime, @vEndTime, NOW(), vType );
	INSERT INTO user_sub(UserNo, SubNo) VALUES(@vUserNo, @vSubNo);
	
	SELECT "DONE" as "DONE" , "0" as "ErrType", "buy_sub" as "Fun","Subscription added correctly" as "Info";
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





