--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.10
-- Dumped by pg_dump version 9.5.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: comfort_levels; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE comfort_levels (
    comfort_level_id smallint NOT NULL,
    comfort_level_name character(20) NOT NULL
);


ALTER TABLE comfort_levels OWNER TO postgres;

--
-- Name: comfort_levels_comfort_level_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comfort_levels_comfort_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comfort_levels_comfort_level_id_seq OWNER TO postgres;

--
-- Name: comfort_levels_comfort_level_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE comfort_levels_comfort_level_id_seq OWNED BY comfort_levels.comfort_level_id;


--
-- Name: excursions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE excursions (
    excursion_id smallint NOT NULL,
    excursion_name character(20) NOT NULL,
    price integer NOT NULL,
    port_id_ports bigint
);


ALTER TABLE excursions OWNER TO postgres;

--
-- Name: excursions_excursion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE excursions_excursion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE excursions_excursion_id_seq OWNER TO postgres;

--
-- Name: excursions_excursion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE excursions_excursion_id_seq OWNED BY excursions.excursion_id;


--
-- Name: many_ports_has_many_ships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE many_ports_has_many_ships (
    port_id_ports bigint NOT NULL,
    ship_id_ships bigint NOT NULL
);


ALTER TABLE many_ports_has_many_ships OWNER TO postgres;

--
-- Name: many_services_has_many_comfort_levels; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE many_services_has_many_comfort_levels (
    service_id_services smallint NOT NULL,
    comfort_level_id_comfort_levels smallint NOT NULL
);


ALTER TABLE many_services_has_many_comfort_levels OWNER TO postgres;

--
-- Name: many_tickets_has_many_excursions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE many_tickets_has_many_excursions (
    ticket_id_tickets bigint NOT NULL,
    excursion_id_excursions smallint NOT NULL
);


ALTER TABLE many_tickets_has_many_excursions OWNER TO postgres;

--
-- Name: ports; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ports (
    port_id bigint NOT NULL,
    port_name character(30) NOT NULL
);


ALTER TABLE ports OWNER TO postgres;

--
-- Name: ports_port_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ports_port_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ports_port_id_seq OWNER TO postgres;

--
-- Name: ports_port_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ports_port_id_seq OWNED BY ports.port_id;


--
-- Name: services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE services (
    service_id smallint NOT NULL,
    service_description character(20) NOT NULL
);


ALTER TABLE services OWNER TO postgres;

--
-- Name: services_service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE services_service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE services_service_id_seq OWNER TO postgres;

--
-- Name: services_service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE services_service_id_seq OWNED BY services.service_id;


--
-- Name: ships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ships (
    ship_id bigint NOT NULL,
    ship_name character(20),
    cruise_duration_days smallint NOT NULL
);


ALTER TABLE ships OWNER TO postgres;

--
-- Name: ships_ship_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ships_ship_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ships_ship_id_seq OWNER TO postgres;

--
-- Name: ships_ship_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ships_ship_id_seq OWNED BY ships.ship_id;


--
-- Name: staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE staff (
    staff_id bigint NOT NULL,
    first_name character(20) NOT NULL,
    last_name character(20) NOT NULL,
    "position" character(20) NOT NULL,
    ship_id_ships bigint
);


ALTER TABLE staff OWNER TO postgres;

--
-- Name: staff_staff_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE staff_staff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE staff_staff_id_seq OWNER TO postgres;

--
-- Name: staff_staff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE staff_staff_id_seq OWNED BY staff.staff_id;


--
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tickets (
    ticket_id bigint NOT NULL,
    discount smallint,
    price integer,
    ability boolean,
    comfort_level_id_comfort_levels smallint,
    ship_id_ships bigint,
    user_id_users bigint
);


ALTER TABLE tickets OWNER TO postgres;

--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tickets_ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tickets_ticket_id_seq OWNER TO postgres;

--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tickets_ticket_id_seq OWNED BY tickets.ticket_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    first_name character(20) NOT NULL,
    last_name character(20) NOT NULL,
    email character(40) NOT NULL,
    password character(20) NOT NULL,
    ship_id_ships bigint
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_user_id_seq OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_user_id_seq OWNED BY users.user_id;


--
-- Name: comfort_level_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comfort_levels ALTER COLUMN comfort_level_id SET DEFAULT nextval('comfort_levels_comfort_level_id_seq'::regclass);


--
-- Name: excursion_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY excursions ALTER COLUMN excursion_id SET DEFAULT nextval('excursions_excursion_id_seq'::regclass);


--
-- Name: port_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ports ALTER COLUMN port_id SET DEFAULT nextval('ports_port_id_seq'::regclass);


--
-- Name: service_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY services ALTER COLUMN service_id SET DEFAULT nextval('services_service_id_seq'::regclass);


--
-- Name: ship_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ships ALTER COLUMN ship_id SET DEFAULT nextval('ships_ship_id_seq'::regclass);


--
-- Name: staff_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staff ALTER COLUMN staff_id SET DEFAULT nextval('staff_staff_id_seq'::regclass);


--
-- Name: ticket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets ALTER COLUMN ticket_id SET DEFAULT nextval('tickets_ticket_id_seq'::regclass);


--
-- Name: user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN user_id SET DEFAULT nextval('users_user_id_seq'::regclass);


--
-- Data for Name: comfort_levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comfort_levels (comfort_level_id, comfort_level_name) FROM stdin;
1	VIP                 
2	LUX                 
3	Standard            
4	Light               
5	Super Light         
\.


--
-- Name: comfort_levels_comfort_level_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comfort_levels_comfort_level_id_seq', 5, true);


--
-- Data for Name: excursions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY excursions (excursion_id, excursion_name, price, port_id_ports) FROM stdin;
1	War museum          	60	1
2	Zoo                 	40	1
3	Art gallery         	50	2
4	Opera               	400	2
5	Museum              	100	3
6	City excursion      	60	7
7	Shopping excursion  	100	6
\.


--
-- Name: excursions_excursion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('excursions_excursion_id_seq', 7, true);


--
-- Data for Name: many_ports_has_many_ships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY many_ports_has_many_ships (port_id_ports, ship_id_ships) FROM stdin;
1	1
2	1
3	1
3	2
4	2
5	2
6	2
1	2
\.


--
-- Data for Name: many_services_has_many_comfort_levels; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY many_services_has_many_comfort_levels (service_id_services, comfort_level_id_comfort_levels) FROM stdin;
1	1
1	2
1	3
1	4
1	5
2	1
2	2
2	3
3	1
3	2
4	1
4	2
4	3
4	4
4	5
5	1
5	2
5	3
6	1
7	1
7	2
\.


--
-- Data for Name: many_tickets_has_many_excursions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY many_tickets_has_many_excursions (ticket_id_tickets, excursion_id_excursions) FROM stdin;
1	1
1	2
8	6
\.


--
-- Data for Name: ports; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ports (port_id, port_name) FROM stdin;
1	Tallin                        
2	Odessa                        
3	Bergen                        
4	Stockholm                     
5	Saint Petersburg              
6	Miami                         
7	New York                      
\.


--
-- Name: ports_port_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ports_port_id_seq', 7, true);


--
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY services (service_id, service_description) FROM stdin;
1	pool                
2	gym                 
3	spa                 
4	unlimited food      
5	unlimited alcohol   
6	beauty salon        
7	captain's dinner    
\.


--
-- Name: services_service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('services_service_id_seq', 7, true);


--
-- Data for Name: ships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ships (ship_id, ship_name, cruise_duration_days) FROM stdin;
1	Titanic             	12
2	Big Cat             	25
\.


--
-- Name: ships_ship_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ships_ship_id_seq', 2, true);


--
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY staff (staff_id, first_name, last_name, "position", ship_id_ships) FROM stdin;
1	Vasiliy             	Vasin               	cook                	1
2	Vasiliy             	Semenov             	waiter              	1
3	Andrey              	Krivoi              	mariner             	1
4	Nikolai             	Popov               	hair stylist        	\N
5	Anna                	Maksimova           	swimming instructor 	\N
6	Helena              	Rubenshtain         	captain             	\N
7	Mark                	Zimberg             	mariner             	\N
\.


--
-- Name: staff_staff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('staff_staff_id_seq', 7, true);


--
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tickets (ticket_id, discount, price, ability, comfort_level_id_comfort_levels, ship_id_ships, user_id_users) FROM stdin;
1	\N	5000	f	1	1	2
2	\N	5000	t	1	1	\N
3	\N	5000	t	1	1	\N
4	\N	4000	t	2	1	\N
5	\N	4000	t	2	1	\N
6	\N	4000	t	2	1	\N
7	\N	1000	t	5	1	\N
8	\N	6500	f	2	2	2
9	\N	6500	t	2	2	\N
10	\N	6500	t	2	2	\N
\.


--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tickets_ticket_id_seq', 10, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (user_id, first_name, last_name, email, password, ship_id_ships) FROM stdin;
2	Alina               	Vasilenko           	alina.vasilenko@gmail.com               	1234                	\N
1	Maria               	Sbitneva            	masha.sbitneva@gmail.com                	1234                	1
3	Christian           	Dior                	cd@gmail.com                            	1234                	\N
4	Egor                	Sbitnev             	egor@gmail.com                          	1234                	\N
5	Stepan              	Sbitnev             	stepan@gmail.com                        	1234                	\N
\.


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_user_id_seq', 5, true);


--
-- Name: comfort_levels_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comfort_levels
    ADD CONSTRAINT comfort_levels_pk PRIMARY KEY (comfort_level_id);


--
-- Name: excursions_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY excursions
    ADD CONSTRAINT excursions_pk PRIMARY KEY (excursion_id);


--
-- Name: many_ports_has_many_ships_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_ports_has_many_ships
    ADD CONSTRAINT many_ports_has_many_ships_pk PRIMARY KEY (port_id_ports, ship_id_ships);


--
-- Name: many_services_has_many_comfort_levels_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_services_has_many_comfort_levels
    ADD CONSTRAINT many_services_has_many_comfort_levels_pk PRIMARY KEY (service_id_services, comfort_level_id_comfort_levels);


--
-- Name: many_tickets_has_many_excursions_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_tickets_has_many_excursions
    ADD CONSTRAINT many_tickets_has_many_excursions_pk PRIMARY KEY (ticket_id_tickets, excursion_id_excursions);


--
-- Name: ports_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ports
    ADD CONSTRAINT ports_pk PRIMARY KEY (port_id);


--
-- Name: services_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY services
    ADD CONSTRAINT services_pk PRIMARY KEY (service_id);


--
-- Name: ships_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ships
    ADD CONSTRAINT ships_pk PRIMARY KEY (ship_id);


--
-- Name: staff_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT staff_pk PRIMARY KEY (staff_id);


--
-- Name: tickets_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT tickets_pk PRIMARY KEY (ticket_id);


--
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- Name: users_uq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_uq UNIQUE (ship_id_ships);


--
-- Name: comfort_levels_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT comfort_levels_fk FOREIGN KEY (comfort_level_id_comfort_levels) REFERENCES comfort_levels(comfort_level_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: comfort_levels_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_services_has_many_comfort_levels
    ADD CONSTRAINT comfort_levels_fk FOREIGN KEY (comfort_level_id_comfort_levels) REFERENCES comfort_levels(comfort_level_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: excursions_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_tickets_has_many_excursions
    ADD CONSTRAINT excursions_fk FOREIGN KEY (excursion_id_excursions) REFERENCES excursions(excursion_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: ports_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY excursions
    ADD CONSTRAINT ports_fk FOREIGN KEY (port_id_ports) REFERENCES ports(port_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: ports_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_ports_has_many_ships
    ADD CONSTRAINT ports_fk FOREIGN KEY (port_id_ports) REFERENCES ports(port_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: services_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_services_has_many_comfort_levels
    ADD CONSTRAINT services_fk FOREIGN KEY (service_id_services) REFERENCES services(service_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: ships_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships) REFERENCES ships(ship_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: ships_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships) REFERENCES ships(ship_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: ships_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_ports_has_many_ships
    ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships) REFERENCES ships(ship_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: ships_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staff
    ADD CONSTRAINT ships_fk FOREIGN KEY (ship_id_ships) REFERENCES ships(ship_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: tickets_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY many_tickets_has_many_excursions
    ADD CONSTRAINT tickets_fk FOREIGN KEY (ticket_id_tickets) REFERENCES tickets(ticket_id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: users_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tickets
    ADD CONSTRAINT users_fk FOREIGN KEY (user_id_users) REFERENCES users(user_id) MATCH FULL ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

