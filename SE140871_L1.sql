CREATE TABLE tblUsers(
	userID varchar(10),
	fullName nvarchar(50),
	password varchar(50),
	status bit,
	PRIMARY KEY (userID)
);

CREATE TABLE tblSuppliers(
	supCode varchar(10),
	supName nvarchar(50),
	address nvarchar(50),
	collaborating bit,
	PRIMARY KEY (supCode)
);

CREATE TABLE tblItems(
	itemCode varchar(10),
	itemName nvarchar(50),
	unit varchar(50),
	price float,
	supplying bit,
	supCode varchar(10),
	PRIMARY KEY (itemCode),
	FOREIGN KEY (supCode) REFERENCES tblSuppliers(supCode)
);

INSERT INTO tblUsers
	VALUES('SE140871', 'Nguyen Hoang Quoc Khanh', '123456', '1');
INSERT INTO tblUsers
	VALUES('SE140110', 'Nguyen Minh Hoang', '123456', '1');
INSERT INTO tblUsers
	VALUES('SE140840', 'Pham Tan Phat', '123456', '1');
INSERT INTO tblUsers
	VALUES('SE140418', 'Cao Dai Thanh', '123456', '1');
INSERT INTO tblUsers
	VALUES('SE140993', 'Do Duong Tam Dang', '123456', '1');

INSERT INTO tblSuppliers
VALUES('DMX', 'Dien May Xanh', '1234 Phan Duy Trinh', '1');
INSERT INTO tblSuppliers
VALUES('PV', 'Phong Vu', '594 Le Van Viet', '1');
INSERT INTO tblSuppliers
VALUES('TGDD', 'The Gioi Di Dong', '486 Do Xuan Hop', '1');
INSERT INTO tblSuppliers
VALUES('NK', 'Nguyen Kim', '264 La Xuan Oai', '1');
INSERT INTO tblSuppliers
VALUES('DMCL', 'Dien May Cho Lon', '546 Vo Van Hat', '1');
INSERT INTO tblSuppliers
VALUES('CS', 'Cellphone S', '93 Vo Van Kiet', '1');

INSERT INTO tblItems
VALUES('I001', 'Laptop DELL', '1-unit', '1000', '0', 'PV');
INSERT INTO tblItems
VALUES('I002', 'Case Transformers', '1-unit', '2500', '1', 'PV');
INSERT INTO tblItems
VALUES('I003', 'Tu lanh Panasonic 188 lit', '1-unit', '300', '1', 'DMX');
INSERT INTO tblItems
VALUES('I004', 'May lanh Shaph Inverter', '1-unit', '520', '1', 'DMX');
INSERT INTO tblItems
VALUES('I005', 'Dien thoai Samsung S20', '1-unit', '1150', '1', 'TGDD');
INSERT INTO tblItems
VALUES('I006', 'Dien thoai Iphone 11 Pro Max', '1-unit', '1200', '1', 'TGDD');
INSERT INTO tblItems
VALUES('I007', 'May loc khong khi Daikin', '1-unit', '400', '0', 'NK');
INSERT INTO tblItems
VALUES('I008', 'May hut bui Phillip', '1-unit', '260', '1', 'NK');
INSERT INTO tblItems
VALUES('I009', 'May giat Shaph', '1-unit', '700', '0', 'DMCL');
INSERT INTO tblItems
VALUES('I010', 'Tu lanh LG 654 lit', '1-unit', '1600', '1', 'DMCL');
INSERT INTO tblItems
VALUES('I011', 'Tai nghe Razer', '1-unit', '150', '0', 'CS');
INSERT INTO tblItems
VALUES('I012', 'Loa bluetooth Sony', '1-unit', '300', '1', 'PV');