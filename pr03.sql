DROP DATABASE IF EXISTS pr03;
CREATE DATABASE pr03;
USE pr03;
CREATE TABLE usuarios (
  login VARCHAR(12),
  clave VARCHAR(12) NOT NULL,
  nombre VARCHAR(12) NOT NULL,
  PRIMARY KEY (login)
);

CREATE TABLE captchas (
  id integer,
  archivo VARCHAR(20),
  cadena VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO usuarios (login,clave, nombre) VALUES
('ana','clave','ana maria'),
('pepe','aaaa','jose'),
('juan','1234','juan jose');

INSERT INTO captchas (id, archivo, cadena) VALUES
(1,'captcha1.png','just example'),
(2,'captcha2.png','good morning'),
(3,'captcha3.png','W6 8HP');