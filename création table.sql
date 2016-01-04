CREATE TABLE JOUEUR(
	id_joueur NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	nom_joueur VARCHAR2(64),
	prenom_joueur VARCHAR2(64),
	qualification VARCHAR2(64) CHECK (VALUES IN ('Qualification', 'Quart de finale', 'Demi-finale', 'Finale')),
	nationalite VARCHAR2(64)
);

INSERT INTO JOUEUR VALUES ('Nadal', 'Raphaël', 'Qualification');
INSERT INTO JOUEUR VALUES ('Federer', 'Roger', 'Qualification');
INSERT INTO JOUEUR VALUES ('Djokovic', 'Novak', 'Qualification');
INSERT INTO JOUEUR VALUES ('Tsonga', 'Jo-Wilfried', 'Qualification');
INSERT INTO JOUEUR VALUES ('Ferrer', 'David', 'Qualification');
INSERT INTO JOUEUR VALUES ('Gasquet', 'Richard', 'Qualification');
INSERT INTO JOUEUR VALUES ('Murray', 'Andy', 'Qualification');
INSERT INTO JOUEUR VALUES ('Raonic', 'Milos', 'Qualification');
/*-------------------------------------------------------------------------*/
CREATE TABLE EQUIPE(
	id_equipe NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	id_joueur1 NUMBER(5),
	id_joueur2 NUMBER(5),
	qualification VARCHAR2(64) CHECK (VALUES IN ('Qualification', 'Quart de finale', 'Demi-finale', 'Finale')),
	FOREIGN KEY (id_joueur1) REFERENCES JOUEUR(id_joueur),
	FOREIGN KEY (id_joueur2) REFERENCES JOUEUR(id_joueur)
);

INSERT INTO EQUIPE VALUES (1, 2, 'Qualification');
INSERT INTO EQUIPE VALUES (3, 4, 'Qualification');
INSERT INTO EQUIPE VALUES (7, 8, 'Qualification');
INSERT INTO EQUIPE VALUES (5, 6, 'Qualification');
/*-------------------------------------------------------------------------*/
CREATE TABLE ARBITRE(
	id_arbitre NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	nom_arbitre VARCHAR2(64),
	prenom_arbitre VARCHAR2(64),
	rank_arbitre VARCHAR2(64),
	nationalite VARCHAR2(64)
);

INSERT INTO ARBITRE VALUES ('DUGELET', 'Aubin', 'JAT2', 'Français');
INSERT INTO ARBITRE VALUES ('DUROT', 'Julien', 'JAT2', 'Anglais');
INSERT INTO ARBITRE VALUES ('MERLE', 'Jeremy', 'JAT2', 'Allemand');
INSERT INTO ARBITRE VALUES ('AUBE', 'Aimée', 'JAT2', 'Espagnol');
INSERT INTO ARBITRE VALUES ('DUGELET', 'Aubin', 'ITT1', 'Serbe');
INSERT INTO ARBITRE VALUES ('SEGUIN', 'Delphine', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES ('DESNOYER', 'Emmanuelle', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES ('MARTINEAU', 'Charmaine', 'ITT1', 'Français');
INSERT INTO ARBITRE VALUES ('LAPIERRE', 'Renée', 'JAT2', 'Français');
INSERT INTO ARBITRE VALUES ('JACQUES', 'Arnaude', 'JAT2', 'Français');
/*-------------------------------------------------------------------------*/
CREATE TABLE RAMASSEUR(
	id_ramasseur NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	nom_ramasseur VARCHAR2(64),
	prenom_ramasseur VARCHAR2(64),
);

INSERT INTO RAMASSEUR VALUES ('PICSOU', 'Baltazar');
INSERT INTO RAMASSEUR VALUES ('RAMBERT', 'Hugo');
INSERT INTO RAMASSEUR VALUES ('LAMBERT', 'Emile');
INSERT INTO RAMASSEUR VALUES ('OTVARD', 'Baldwin');
INSERT INTO RAMASSEUR VALUES ('GERVIN', 'Ditbert');
INSERT INTO RAMASSEUR VALUES ('GUNTER', 'Alwin');
INSERT INTO RAMASSEUR VALUES ('INGVALD', 'Almar');
INSERT INTO RAMASSEUR VALUES ('DUHAMEL' , 'Damien');
INSERT INTO RAMASSEUR VALUES ('BONAMI', 'Orville');
INSERT INTO RAMASSEUR VALUES ('GAGNON', 'Claude');
/*------------------------------------------------------------------------*/
CREATE TABLE COURT(
	id_court NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	nom_court VARCHAR2(64),
	nb_places NUMBER(5)
);

INSERT INTO COURT VALUES ('Grand Court de Gerlan', 1100);
INSERT INTO COURT VALUES ('Court de Saint-Andre', 600);
INSERT INTO COURT VALUES ('Moyen Court', 330);
INSERT INTO COURT VALUES ('Golden Court', 850);
/*-----------------------------------------------------------------------*/
CREATE TABLE MATCHS(
	id_match NUMBER(5) PRIMARY KEY AUTO_INCREMENT,
	date_match DATE,
	creneau_match VARCHAR2(4) CHECK (VALUES IN ('8am', '11am', '3pm', '6pm', '9pm')),
	categorie_match VARCHAR2(64) CHECK (VALUES IN('Simple Homme', 'Simple Femme', 'Double Homme', 'Double Femme')),
	tour_match VARCHAR2(64) CHECK (VALUES IN ('Qualification', 'Quart de finale', 'Demi-finale', 'Finale')),
	id_court NUMBER(5),
	FOREIGN KEY (id_court) REFERENCES COURT(id_court)
);

INSERT INTO MATCHS VALUES (TO_DATE('2016-01-05', 'yyyy-mm-dd'), '8am', 'Simple Homme', 'Qualification', 1);
INSERT INTO MATCHS
/*-----------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_JOUEUR(
	id_match NUMBER(5),
	id_joueur NUMBER(5),
	PRIMARY KEY(id_match,id_joueur),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_joueur) REFERENCES JOUEUR(id_joueur)
);
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
	id_match NUMBER(5),
	id_ramasseur NUMBER(5),
	cote VARCHAR2(64),
	PRIMARY KEY(id_match,id_ramasseur),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_ramasseur) REFERENCES RAMASSEUR(id_ramasseur)
);
/*-----------------------------------------------------------------------*/
CREATE TABLE ASSIGNEMENT_EQUIPE(
	id_match NUMBER(5),
	id_equipe NUMBER(5),
	PRIMARY KEY(id_match,id_equipe),
	FOREIGN KEY (id_match) REFERENCES MATCHS(id_match),
	FOREIGN KEY (id_equipe) REFERENCES EQUIPE(id_equipe)
);
