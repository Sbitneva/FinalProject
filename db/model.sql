-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.0
-- PostgreSQL version: 9.6
-- Project Site: pgmodeler.com.br
-- Model Author: ---

-- object: "user" | type: ROLE --
-- DROP ROLE IF EXISTS "user";
CREATE ROLE "user" WITH 
	LOGIN
	ENCRYPTED PASSWORD 'user';
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: cruise_company | type: DATABASE --
-- -- DROP DATABASE IF EXISTS cruise_company;
-- CREATE DATABASE cruise_company
-- 	ENCODING = 'UTF8'
-- 	LC_COLLATE = 'en_US.UTF-8'
-- 	LC_CTYPE = 'en_US.UTF-8'
-- 	TABLESPACE = pg_default
-- 	OWNER = postgres
-- ;
-- -- ddl-end --
-- 

-- object: public.users | type: TABLE --
-- DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users(
	user_id bigserial NOT NULL,
	login character(20) NOT NULL,
	password character(20) NOT NULL,
	first_name character(20) NOT NULL,
	last_name character(20) NOT NULL,
	ship_id_ships bigint,
	CONSTRAINT users_pk PRIMARY KEY (user_id)

);
-- ddl-end --
ALTER TABLE public.users OWNER TO postgres;
-- ddl-end --

-- object: public.tickets | type: TABLE --
-- DROP TABLE IF EXISTS public.tickets CASCADE;
CREATE TABLE public.tickets(
	ticket_id bigserial NOT NULL,
	discount smallint,
	price integer,
	ability bool,
	owner integer NOT NULL,
	comfort_level_id_comfort_levels smallint,
	ship_id_ships bigint,
	user_id_users bigint,
	CONSTRAINT tickets_pk PRIMARY KEY (ticket_id,owner)

);
-- ddl-end --
ALTER TABLE public.tickets OWNER TO postgres;
-- ddl-end --

-- object: public.comfort_levels | type: TABLE --
-- DROP TABLE IF EXISTS public.comfort_levels CASCADE;
CREATE TABLE public.comfort_levels(
	comfort_level_id smallserial NOT NULL,
	comfort_level_name character(20) NOT NULL,
	pool bool NOT NULL DEFAULT true,
	gym bool NOT NULL DEFAULT true,
	beaty_salon bool NOT NULL DEFAULT true,
	spa bool NOT NULL DEFAULT true,
	unlimited_alcohol bool NOT NULL DEFAULT true,
	unlimited_food bool NOT NULL DEFAULT true,
	CONSTRAINT comfort_levels_pk PRIMARY KEY (comfort_level_id)

);
-- ddl-end --
ALTER TABLE public.comfort_levels OWNER TO postgres;
-- ddl-end --

-- object: comfort_levels_fk | type: CONSTRAINT --
-- ALTER TABLE public.tickets DROP CONSTRAINT IF EXISTS comfort_levels_fk CASCADE;
ALTER TABLE public.tickets ADD CONSTRAINT comfort_levels_fk FOREIGN KEY (comfort_level_id_comfort_levels)
REFERENCES public.comfort_levels (comfort_level_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.ships | type: TABLE --
-- DROP TABLE IF EXISTS public.ships CASCADE;
CREATE TABLE public.ships(
	ship_id bigserial NOT NULL,
	ship_name character(20),
	cruise_duration_days smallint NOT NULL,
	staff_id_staff bigint,
	CONSTRAINT ships_pk PRIMARY KEY (ship_id)

);
-- ddl-end --
ALTER TABLE public.ships OWNER TO postgres;
-- ddl-end --

-- object: ships_fk | type: CONSTRAINT --
-- ALTER TABLE public.tickets DROP CONSTRAINT IF EXISTS ships_fk CASCADE;
ALTER TABLE public.tickets ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships)
REFERENCES public.ships (ship_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.staff | type: TABLE --
-- DROP TABLE IF EXISTS public.staff CASCADE;
CREATE TABLE public.staff(
	staff_id bigserial NOT NULL,
	first_name character(20) NOT NULL,
	last_name character(20) NOT NULL,
	"position" character(20) NOT NULL,
	CONSTRAINT staff_pk PRIMARY KEY (staff_id)

);
-- ddl-end --
ALTER TABLE public.staff OWNER TO postgres;
-- ddl-end --

-- object: public.excursions | type: TABLE --
-- DROP TABLE IF EXISTS public.excursions CASCADE;
CREATE TABLE public.excursions(
	excursion_id smallserial NOT NULL,
	excursion_name character(20) NOT NULL,
	price integer NOT NULL,
	port_id_ports bigint,
	CONSTRAINT excursions_pk PRIMARY KEY (excursion_id)

);
-- ddl-end --
ALTER TABLE public.excursions OWNER TO postgres;
-- ddl-end --

-- object: public.ports | type: TABLE --
-- DROP TABLE IF EXISTS public.ports CASCADE;
CREATE TABLE public.ports(
	port_id bigserial NOT NULL,
	port_name smallint NOT NULL,
	CONSTRAINT ports_pk PRIMARY KEY (port_id)

);
-- ddl-end --
ALTER TABLE public.ports OWNER TO postgres;
-- ddl-end --

-- object: ports_fk | type: CONSTRAINT --
-- ALTER TABLE public.excursions DROP CONSTRAINT IF EXISTS ports_fk CASCADE;
ALTER TABLE public.excursions ADD CONSTRAINT ports_fk FOREIGN KEY (port_id_ports)
REFERENCES public.ports (port_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: ships_fk | type: CONSTRAINT --
-- ALTER TABLE public.users DROP CONSTRAINT IF EXISTS ships_fk CASCADE;
ALTER TABLE public.users ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships)
REFERENCES public.ships (ship_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: users_uq | type: CONSTRAINT --
-- ALTER TABLE public.users DROP CONSTRAINT IF EXISTS users_uq CASCADE;
ALTER TABLE public.users ADD CONSTRAINT users_uq UNIQUE (ship_id_ships);
-- ddl-end --

-- object: staff_fk | type: CONSTRAINT --
-- ALTER TABLE public.ships DROP CONSTRAINT IF EXISTS staff_fk CASCADE;
ALTER TABLE public.ships ADD CONSTRAINT staff_fk FOREIGN KEY (staff_id_staff)
REFERENCES public.staff (staff_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: ships_uq | type: CONSTRAINT --
-- ALTER TABLE public.ships DROP CONSTRAINT IF EXISTS ships_uq CASCADE;
ALTER TABLE public.ships ADD CONSTRAINT ships_uq UNIQUE (staff_id_staff);
-- ddl-end --

-- object: users_fk | type: CONSTRAINT --
-- ALTER TABLE public.tickets DROP CONSTRAINT IF EXISTS users_fk CASCADE;
ALTER TABLE public.tickets ADD CONSTRAINT users_fk FOREIGN KEY (user_id_users)
REFERENCES public.users (user_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.many_ports_has_many_ships | type: TABLE --
-- DROP TABLE IF EXISTS public.many_ports_has_many_ships CASCADE;
CREATE TABLE public.many_ports_has_many_ships(
	port_id_ports bigint NOT NULL,
	ship_id_ships bigint NOT NULL,
	CONSTRAINT many_ports_has_many_ships_pk PRIMARY KEY (port_id_ports,ship_id_ships)

);
-- ddl-end --

-- object: ports_fk | type: CONSTRAINT --
-- ALTER TABLE public.many_ports_has_many_ships DROP CONSTRAINT IF EXISTS ports_fk CASCADE;
ALTER TABLE public.many_ports_has_many_ships ADD CONSTRAINT ports_fk FOREIGN KEY (port_id_ports)
REFERENCES public.ports (port_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: ships_fk | type: CONSTRAINT --
-- ALTER TABLE public.many_ports_has_many_ships DROP CONSTRAINT IF EXISTS ships_fk CASCADE;
ALTER TABLE public.many_ports_has_many_ships ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships)
REFERENCES public.ships (ship_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


