--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2019-12-24 15:00:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2893 (class 0 OID 16410)
-- Dependencies: 206
-- Data for Name: matches; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.matches (id, state, creator_id, user_id, match_name) VALUES (1, 'e', 2, '{3,4,5}', NULL);
INSERT INTO public.matches (id, state, creator_id, user_id, match_name) VALUES (2, 'e', 2, '{3,4,5}', NULL);
INSERT INTO public.matches (id, state, creator_id, user_id, match_name) VALUES (3, 'e', 2, '{3,4,5}', NULL);
INSERT INTO public.matches (id, state, creator_id, user_id, match_name) VALUES (4, 'e', 2, '{3,4,5}', NULL);
INSERT INTO public.matches (id, state, creator_id, user_id, match_name) VALUES (5, 'e', 2, '{3,4,5}', NULL);


--
-- TOC entry 2891 (class 0 OID 16395)
-- Dependencies: 204
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (4, 'utente1', 'cognome1', 'mail1@.it', '1234', 'utente1', 'p', NULL, '2019-12-03 18:40:25.127159+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (5, 'utente2', 'cognome2', 'mail2@.it', '1234', 'utente2', 'p', NULL, '2019-12-03 18:40:25.127773+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (6, 'utente3', 'cognome3', 'mail3@.it', '1234', 'utente3', 'p', NULL, '2019-12-03 18:40:25.128309+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (7, 'utente4', 'cognome4', 'mail4@.it', '1234', 'utente4', 'p', NULL, '2019-12-03 18:40:25.128896+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (8, 'utente5', 'cognome5', 'mail5@.it', '1234', 'utente5', 'p', NULL, '2019-12-03 18:40:25.129438+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (9, 'utente6', 'cognome6', 'mail6@.it', '1234', 'utente6', 'p', NULL, '2019-12-03 18:40:25.129884+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (10, 'utente7', 'cognome7', 'mail7@.it', '1234', 'utente7', 'p', NULL, '2019-12-03 18:40:25.130407+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (11, 'utente8', 'cognome8', 'mail8@.it', '1234', 'utente8', 'p', NULL, '2019-12-03 18:40:25.130897+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (12, 'utente9', 'cognome9', 'mail9@.it', '1234', 'utente9', 'p', NULL, '2019-12-03 18:40:25.131371+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (2, 'dio', 'brando', 'i@i.it', 'boia', 'nick', 'a', NULL, '2019-11-24 19:20:04.702606+01');
INSERT INTO public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) VALUES (3, 'pippo', 'kujo', 'mail0@.it', '1234', 'utente0', 'p', NULL, '2019-12-21 08:59:51.577419+01');


--
-- TOC entry 2895 (class 0 OID 16421)
-- Dependencies: 208
-- Data for Name: sentences; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (44, NULL, 'SUPERARE ESAMI AL PRIMO COLPO', 'SPERANZA DEGLI STUDENTI', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (45, NULL, 'VINSERO BATTAGLIE GRAZIE ALLA LORO FOGA', 'LE AMAZZONI', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (46, NULL, 'LASCIA IL BATTERISTA D''ORAZIO', 'DIVORZIO IN CASA POOH', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (47, NULL, 'TENGO FAMIGLIA', 'MI GIUSTIFICO ALL''ITALIANA', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (48, NULL, 'EROE MARVEL IRONMAN', 'ROBERT DOWNEY JR', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (49, NULL, 'PRINCIPE SPOSTA UN''ATTRICE', 'MATRIMONIO REALE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (50, NULL, 'FATTI MANDARE DALLA MAMMA', 'SUCCESSO DI MORANDI', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (51, NULL, 'NON POTEVANO RESTARE DA SOLI', 'I FIDANZATI DI UNA VOLTA', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (55, NULL, 'NON DIRE MAI COME FINISCE IL FILM', 'BON TON AL CINEMA', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (59, NULL, 'SONO QUELLE RUBATE', 'LE FOTO PIU'' BELLE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (63, 2, 'PARLARE DEL PIU'' E DEL MENO', 'DIALOGO FRA MATEMATICI', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (64, 2, 'LE FACCINE DEGLI EMOTICON', 'ALLEGRIA! SUL CELLULARE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (65, 2, 'NE DICONO IL DOPPIO DELLE DONNE', 'UOMINI E PAROLACCE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (66, 2, 'PRIMA LA DOCCIA POI IL DOPOSOLE', 'TORNATI DAL MARE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (67, 2, 'LA CANZONE DANCE SEXY', 'UN SUCCESSO STRABALLATO', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (68, 2, 'UN BRACCIALE CHE PROTEGGE DAL SOLE', 'NOVITA'' SULLA SPIAGGIA', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (69, 2, 'MANO FREDDA CUORE CALDO', 'TIMIDEZZA PROVERBIALE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (70, 2, 'SONO ARRIVATE LE STANGATE FISCALI', 'TASSE', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (71, 2, 'SIGLA DI TESTA E TITOLI DI CODA', 'L''INIZIO E LA FINE DI UN PROGRAMMA', NULL);
INSERT INTO public.sentences (id, create_by_user, sentence, hint, seen_by_user) VALUES (72, 2, 'UN QUALUNQUISTA NON HA IDEE POLITICHE', 'UN MODO DI PENSARE', NULL);


--
-- TOC entry 2897 (class 0 OID 16437)
-- Dependencies: 210
-- Data for Name: manches; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2902 (class 0 OID 16607)
-- Dependencies: 215
-- Data for Name: actions; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2898 (class 0 OID 16458)
-- Dependencies: 211
-- Data for Name: verifications; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2908 (class 0 OID 0)
-- Dependencies: 212
-- Name: Actions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Actions_id_seq"', 1, false);


--
-- TOC entry 2909 (class 0 OID 0)
-- Dependencies: 213
-- Name: Actions_manche_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Actions_manche_id_seq"', 1, false);


--
-- TOC entry 2910 (class 0 OID 0)
-- Dependencies: 214
-- Name: Actions_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Actions_player_id_seq"', 1, false);


--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 209
-- Name: manches_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.manches_id_seq', 1, false);


--
-- TOC entry 2912 (class 0 OID 0)
-- Dependencies: 205
-- Name: match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.match_id_seq', 5, true);


--
-- TOC entry 2913 (class 0 OID 0)
-- Dependencies: 207
-- Name: sentences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sentences_id_seq', 72, true);


--
-- TOC entry 2914 (class 0 OID 0)
-- Dependencies: 203
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.user_id_seq', 12, true);


-- Completed on 2019-12-24 15:00:05

--
-- PostgreSQL database dump complete
--

