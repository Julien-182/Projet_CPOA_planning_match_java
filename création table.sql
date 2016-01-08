CREATE TABLE JOUEUR(
	id_joueur NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	nom_joueur VARCHAR2(64),
	prenom_joueur VARCHAR2(64),
	qualification ENUM('Qualification' ,'Quart de finale' ,'Demi-finale','Finale'),
	nationalite VARCHAR2(64)
	sexe VARCHAR2(5)
); /*FAIT*/

INSERT INTO JOUEUR VALUES (1,'Nadal', 'Raphaël', 'Qualification', 'Français', 'Homme');
INSERT INTO JOUEUR VALUES (2,'Federer', 'Roger', 'Qualification', 'Suisse', 'Homme');
INSERT INTO JOUEUR VALUES (3,'Djokovic', 'Novak', 'Qualification', 'Serbe', 'Homme');
INSERT INTO JOUEUR VALUES (4,'Tsonga', 'Jo-Wilfried', 'Qualification', 'Français', 'Homme');
INSERT INTO JOUEUR VALUES (5,'Ferrer', 'David', 'Qualification', 'Espagnol', 'Homme');
INSERT INTO JOUEUR VALUES (6,'Gasquet', 'Richard', 'Qualification','Français', 'Homme');
INSERT INTO JOUEUR VALUES (7,'Murray', 'Andy', 'Qualification', 'Américain', 'Homme');
INSERT INTO JOUEUR VALUES (8,'Raonic', 'Milos', 'Qualification', 'Anglais', 'Homme');
INSERT INTO JOUEUR VALUES (9,'Berdych', 'Thomas', 'Qualification', 'RTC', 'Homme');
INSERT INTO JOUEUR VALUES (10,'Isner', 'John', 'Qualification', 'USA', 'Homme');
INSERT INTO JOUEUR VALUES (11,'Anderson', 'Kevin', 'Qualification', 'SER', 'Homme');
INSERT INTO JOUEUR VALUES (12,'Cilic', 'Marin', 'Qualification', 'CRO', 'Homme');
INSERT INTO JOUEUR VALUES (13,'Simon', 'Gilles', 'Qualification', 'FRA', 'Homme');
INSERT INTO JOUEUR VALUES (14,'Goffin', 'David', 'Qualification','BEL', 'Homme');
INSERT INTO JOUEUR VALUES (15,'Lopez', 'Feliciano', 'Qualification', 'ESP', 'Homme');
INSERT INTO JOUEUR VALUES (16,'Tomic', 'Bernard', 'Qualification', 'AUS', 'Homme');

INSERT INTO JOUEUR VALUES(17,'Williams','Serena','Qualification','USA','Femme');
INSERT INTO JOUEUR VALUES(18,'Halep','Simona','Qualification','ROU','Femme');
INSERT INTO JOUEUR VALUES(19,'Muguruza','Garbine','Qualification','ESP','Femme');
INSERT INTO JOUEUR VALUES(20,'Sharapova','Maria','Qualification','RUS','Femme');
INSERT INTO JOUEUR VALUES(21,'Radwanska','Agnieszka','Qualification','POL','Femme');
INSERT INTO JOUEUR VALUES(22,'Kvitova','Petra','Qualification','RTC','Femme');
INSERT INTO JOUEUR VALUES(23,'Williams','Venus','Qualification','USA','Femme');
INSERT INTO JOUEUR VALUES(24,'Pennetta','Flavia','Qualification','ITA','Femme');
INSERT INTO JOUEUR VALUES(25,'Safarova','Lucie','Qualification','RTC','Femme');
INSERT INTO JOUEUR VALUES(26,'Kerber','Angelique','Qualification','ALL','Femme');
INSERT INTO JOUEUR VALUES(27,'Pliskova','Karolina','Qualification','RTC','Femme');
INSERT INTO JOUEUR VALUES(28,'Bacsinszky','Timea','Qualification','SUI','Femme');
INSERT INTO JOUEUR VALUES(29,'Suarez Navarro','Carla','Qualification','ESP','Femme');
INSERT INTO JOUEUR VALUES(30,'Bencic','Belinda','Qualification','SUI','Femme');
INSERT INTO JOUEUR VALUES(31,'Vinci','Roberta','Qualification','ITA','Femme');
INSERT INTO JOUEUR VALUES(32,'Ivanovic','Ana','Qualification','SER','Femme');
/*-------------------------------------------------------------------------*/
CREATE TABLE EQUIPE(
	id_equipe NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	id_joueur1 NUMBER(5),
	id_joueur2 NUMBER(5),
	qualification ENUM('Qualification','Quart de finale','Demi-finale','Finale'),
	FOREIGN KEY (id_joueur1) REFERENCES JOUEUR(id_joueur),
	FOREIGN KEY (id_joueur2) REFERENCES JOUEUR(id_joueur)
); /*FAIT*/

INSERT INTO EQUIPE VALUES (1,1, 2, 'Qualification');
INSERT INTO EQUIPE VALUES (2,3, 4, 'Qualification');
INSERT INTO EQUIPE VALUES (3,5, 6, 'Qualification');
INSERT INTO EQUIPE VALUES (4,7, 8, 'Qualification');
/*-------------------------------------------------------------------------*/
CREATE TABLE ARBITRE(
	id_arbitre NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	nom_arbitre VARCHAR2(64),
	prenom_arbitre VARCHAR2(64),
	rank_arbitre VARCHAR2(64),
	nationalite VARCHAR2(64)
); /*FAIT*/

INSERT INTO ARBITRE VALUES (1,'DUGELET', 'Aubin', 'JAT2', 'Français');
INSERT INTO ARBITRE VALUES (2,'DUROT', 'Julien', 'JAT2', 'Anglais');
INSERT INTO ARBITRE VALUES (3,'MERLE', 'Jeremy', 'JAT2', 'Allemand');
INSERT INTO ARBITRE VALUES (4,'AUBE', 'Aimée', 'JAT2', 'Espagnol');
INSERT INTO ARBITRE VALUES (5,'DUGELET', 'Aubin', 'ITT1', 'Serbe');
INSERT INTO ARBITRE VALUES (6,'SEGUIN', 'Delphine', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES (7,'DESNOYER', 'Emmanuelle', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES (8,'MARTINEAU', 'Charmaine', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES (9,'LAPIERRE', 'Renée', 'JAT2', 'Français');
INSERT INTO ARBITRE VALUES (10,'JACQUES', 'Arnaude', 'JAT2', 'Français');
/*-------------------------------------------------------------------------*/
CREATE TABLE RAMASSEUR(
	id_ramasseur NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	nom_ramasseur VARCHAR2(64),
	prenom_ramasseur VARCHAR2(64),
); /*FAIT*/

INSERT INTO RAMASSEUR VALUES (1,'PICSOU', 'Baltazar');
INSERT INTO RAMASSEUR VALUES (2,'RAMBERT', 'Hugo');
INSERT INTO RAMASSEUR VALUES (3,'LAMBERT', 'Emile');
INSERT INTO RAMASSEUR VALUES (4,'OTVARD', 'Baldwin');
INSERT INTO RAMASSEUR VALUES (5,'GERVIN', 'Ditbert');
INSERT INTO RAMASSEUR VALUES (6,'GUNTER', 'Alwin');

INSERT INTO RAMASSEUR VALUES (7,'INGVALD', 'Almar');
INSERT INTO RAMASSEUR VALUES (8,'DUHAMEL' , 'Damien');
INSERT INTO RAMASSEUR VALUES (9,'BONAMI', 'Orville');
INSERT INTO RAMASSEUR VALUES (10,'GAGNON', 'Claude');
INSERT INTO RAMASSEUR VALUES (11,'LEVESQUE', 'Annot');
INSERT INTO RAMASSEUR VALUES (12,'LAVALLEE', 'Avril');

INSERT INTO RAMASSEUR VALUES (13,'VERRONEAU', 'Maryse');
INSERT INTO RAMASSEUR VALUES (14,'COURSE', 'Vignette');
INSERT INTO RAMASSEUR VALUES (15,'BLER', 'Yolande');
INSERT INTO RAMASSEUR VALUES (16,'POLIVKA', 'Bohumil');
INSERT INTO RAMASSEUR VALUES (17,'CSEKE', 'Balogh');
INSERT INTO RAMASSEUR VALUES (18,'GISBORN', 'Michael');

INSERT INTO RAMASSEUR VALUES (19,'ETHERIDGE', 'Benjamin');
INSERT INTO RAMASSEUR VALUES (20,'STOUT', 'Caitlin');
INSERT INTO RAMASSEUR VALUES (21,'GOLDIE', 'Lara');
INSERT INTO RAMASSEUR VALUES (22,'LAMONT', 'Amelie');
INSERT INTO RAMASSEUR VALUES (23,'DALEY-SCOTT', 'Harrison');
INSERT INTO RAMASSEUR VALUES (24,'CLARK', 'Elijah');
/*------------------------------------------------------------------------*/
CREATE TABLE EQUIPE_RAMASSEURS(
	id_equipe_ramasseurs INT(5),
	nom_equipe VARCHAR(64)
);/*FAIT*/
INSERT INTO EQUIPE_RAMASSEURS VALUES (1,'Equipe ramasseurs 1');
INSERT INTO EQUIPE_RAMASSEURS VALUES (2,'Equipe ramasseurs 2');
INSERT INTO EQUIPE_RAMASSEURS VALUES (3,'Equipe ramasseurs 3');
INSERT INTO EQUIPE_RAMASSEURS VALUES (4,'Equipe ramasseurs 4');
/*------------------------------------------------------------------------*/
CREATE TABLE COURT(
	id_court NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	nom_court VARCHAR2(64),
	nb_places NUMBER(5)
); /*FAIT*/

INSERT INTO COURT VALUES (1,'Grand Court de Gerlan', 1100);
INSERT INTO COURT VALUES (2,'Court de Saint-Andre', 600);
INSERT INTO COURT VALUES (3,'Moyen Court', 330);
INSERT INTO COURT VALUES (4,'Golden Court', 850);
/*-----------------------------------------------------------------------*/
CREATE TABLE MATCHS(
	id_match NUMBER(5) AUTO_INCREMENT PRIMARY KEY,
	date_match DATE,
	creneau_match ENUM('8am', '11am', '3pm', '6pm', '9pm'),
	categorie_match ENUM('Simple Homme', 'Simple Femme', 'Double Homme', 'Double Femme'),
	tour_match ENUM('Qualification','Quart de finale','Demi-finale','Finale'),
	id_court NUMBER(5),
	FOREIGN KEY (id_court) REFERENCES COURT(id_court)
); /*FAIT*/

INSERT INTO MATCHS VALUES (TO_DATE('2016-01-05', 'yyyy-mm-dd'), '8am', 'Simple Homme', 'Qualification', 1);
/*-----------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_JOUEUR(
	id_match NUMBER(5),
	id_joueur NUMBER(5),
	PRIMARY KEY(id_match,id_joueur),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_joueur) REFERENCES JOUEUR(id_joueur)
); /*FAIT*/
/*-----------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_ARBITRE(
	id_match NUMBER(5),
	id_arbitre NUMBER(5),
	categorie_arbitre VARCHAR2(64),
	PRIMARY KEY(id_match,id_arbitre),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_arbitre) REFERENCES ARBITRE(id_arbitre)
);
/*------------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_RAMASSEUR(
	id_equipe_ramasseurs NUMBER(5),
	id_ramasseur NUMBER(5),
	PRIMARY KEY(id_match,id_ramasseur),
	FOREIGN KEY (id_equipe_ramasseurs) REFERENCES EQUIPE_RAMASSEURS(id_match),
	FOREIGN KEY (id_ramasseur) REFERENCES RAMASSEUR(id_ramasseur)
);

INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,1);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,2);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,3);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,4);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,5);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (1,6);

INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,7);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,8);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,9);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,10);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,11);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (2,12);

INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,13);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,14);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,15);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,16);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,17);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (3,18);

INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,19);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,20);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,21);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,22);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,23);
INSERT INTO ASSIGNEMENT_RAMASSEUR VALUES (4,24);
/*-----------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_EQUIPE(
	id_match NUMBER(5),
	id_equipe NUMBER(5),
	PRIMARY KEY(id_match,id_equipe),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_equipe) REFERENCES EQUIPE(id_equipe)
);
