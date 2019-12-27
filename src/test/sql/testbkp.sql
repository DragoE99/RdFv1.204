--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2019-12-23 14:01:43

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
-- TOC entry 1 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2908 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 228 (class 1255 OID 16596)
-- Name: sentence_insert(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sentence_insert(new_sentence character varying, new_hint character varying, creator integer DEFAULT NULL::integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$DECLARE
counter    INTEGER := 0;
BEGIN
	IF NOT EXISTS
		(SELECT* FROM sentences
		WHERE sentence = new_sentence)
	THEN 
		INSERT INTO sentences (sentence, hint, create_by_user)
		VALUES(new_sentence,new_hint, creator);
		counter=1;
	END IF;
RETURN counter;
END
		$$;


ALTER FUNCTION public.sentence_insert(new_sentence character varying, new_hint character varying, creator integer) OWNER TO postgres;

--
-- TOC entry 2909 (class 0 OID 0)
-- Dependencies: 228
-- Name: FUNCTION sentence_insert(new_sentence character varying, new_hint character varying, creator integer); Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON FUNCTION public.sentence_insert(new_sentence character varying, new_hint character varying, creator integer) IS 'funzione per inserimento di una frase';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16607)
-- Name: actions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actions (
    id integer NOT NULL,
    turn integer,
    manche_id integer NOT NULL,
    player_id integer NOT NULL,
    action_name character varying(255) NOT NULL,
    jolly boolean NOT NULL,
    action_wallet integer DEFAULT 0 NOT NULL,
    player_number integer
);


ALTER TABLE public.actions OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16601)
-- Name: Actions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Actions_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Actions_id_seq" OWNER TO postgres;

--
-- TOC entry 2910 (class 0 OID 0)
-- Dependencies: 212
-- Name: Actions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Actions_id_seq" OWNED BY public.actions.id;


--
-- TOC entry 213 (class 1259 OID 16603)
-- Name: Actions_manche_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Actions_manche_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Actions_manche_id_seq" OWNER TO postgres;

--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 213
-- Name: Actions_manche_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Actions_manche_id_seq" OWNED BY public.actions.manche_id;


--
-- TOC entry 214 (class 1259 OID 16605)
-- Name: Actions_player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Actions_player_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Actions_player_id_seq" OWNER TO postgres;

--
-- TOC entry 2912 (class 0 OID 0)
-- Dependencies: 214
-- Name: Actions_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Actions_player_id_seq" OWNED BY public.actions.player_id;


--
-- TOC entry 210 (class 1259 OID 16437)
-- Name: manches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manches (
    id integer NOT NULL,
    match_id integer NOT NULL,
    sentence_id integer,
    seen_by_user integer[],
    manche_wallet_p1 integer DEFAULT 0 NOT NULL,
    manche_walletp2 integer DEFAULT 0 NOT NULL,
    manche_wallet_p3 integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.manches OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16435)
-- Name: manches_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.manches_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.manches_id_seq OWNER TO postgres;

--
-- TOC entry 2913 (class 0 OID 0)
-- Dependencies: 209
-- Name: manches_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.manches_id_seq OWNED BY public.manches.id;


--
-- TOC entry 206 (class 1259 OID 16410)
-- Name: matches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.matches (
    id integer NOT NULL,
    state "char" NOT NULL,
    creator_id integer NOT NULL,
    user_id integer[],
    match_name character varying(255)
);


ALTER TABLE public.matches OWNER TO postgres;

--
-- TOC entry 2914 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN matches.state; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.matches.state IS 'Stato della partita';


--
-- TOC entry 205 (class 1259 OID 16408)
-- Name: match_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.match_id_seq OWNER TO postgres;

--
-- TOC entry 2915 (class 0 OID 0)
-- Dependencies: 205
-- Name: match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.match_id_seq OWNED BY public.matches.id;


--
-- TOC entry 208 (class 1259 OID 16421)
-- Name: sentences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sentences (
    id integer NOT NULL,
    create_by_user integer,
    sentence character varying(255) NOT NULL,
    hint character varying(255) NOT NULL,
    seen_by_user integer[]
);


ALTER TABLE public.sentences OWNER TO postgres;

--
-- TOC entry 2916 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN sentences.create_by_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.sentences.create_by_user IS 'id del creatore della frase';


--
-- TOC entry 207 (class 1259 OID 16419)
-- Name: sentences_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sentences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sentences_id_seq OWNER TO postgres;

--
-- TOC entry 2917 (class 0 OID 0)
-- Dependencies: 207
-- Name: sentences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sentences_id_seq OWNED BY public.sentences.id;


--
-- TOC entry 204 (class 1259 OID 16395)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    mail character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    nickname character varying(255),
    role "char" NOT NULL,
    creation_date date DEFAULT CURRENT_TIMESTAMP,
    last_change_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16393)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 2918 (class 0 OID 0)
-- Dependencies: 203
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public.users.id;


--
-- TOC entry 211 (class 1259 OID 16458)
-- Name: verifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.verifications (
    user_id integer NOT NULL,
    verification_code character varying(255) NOT NULL,
    user_mail character varying(255) NOT NULL
);


ALTER TABLE public.verifications OWNER TO postgres;

--
-- TOC entry 2735 (class 2604 OID 16610)
-- Name: actions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions ALTER COLUMN id SET DEFAULT nextval('public."Actions_id_seq"'::regclass);


--
-- TOC entry 2736 (class 2604 OID 16611)
-- Name: actions manche_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions ALTER COLUMN manche_id SET DEFAULT nextval('public."Actions_manche_id_seq"'::regclass);


--
-- TOC entry 2737 (class 2604 OID 16612)
-- Name: actions player_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions ALTER COLUMN player_id SET DEFAULT nextval('public."Actions_player_id_seq"'::regclass);


--
-- TOC entry 2731 (class 2604 OID 16563)
-- Name: manches id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manches ALTER COLUMN id SET DEFAULT nextval('public.manches_id_seq'::regclass);


--
-- TOC entry 2729 (class 2604 OID 16573)
-- Name: matches id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches ALTER COLUMN id SET DEFAULT nextval('public.match_id_seq'::regclass);


--
-- TOC entry 2730 (class 2604 OID 16502)
-- Name: sentences id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sentences ALTER COLUMN id SET DEFAULT nextval('public.sentences_id_seq'::regclass);


--
-- TOC entry 2727 (class 2604 OID 16469)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 2902 (class 0 OID 16607)
-- Dependencies: 215
-- Data for Name: actions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actions (id, turn, manche_id, player_id, action_name, jolly, action_wallet, player_number) FROM stdin;
\.


--
-- TOC entry 2897 (class 0 OID 16437)
-- Dependencies: 210
-- Data for Name: manches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manches (id, match_id, sentence_id, seen_by_user, manche_wallet_p1, manche_walletp2, manche_wallet_p3) FROM stdin;
\.


--
-- TOC entry 2893 (class 0 OID 16410)
-- Dependencies: 206
-- Data for Name: matches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.matches (id, state, creator_id, user_id, match_name) FROM stdin;
1	e	2	{3,4,5}	\N
2	e	2	{3,4,5}	\N
3	e	2	{3,4,5}	\N
4	e	2	{3,4,5}	\N
5	e	2	{3,4,5}	\N
\.


--
-- TOC entry 2895 (class 0 OID 16421)
-- Dependencies: 208
-- Data for Name: sentences; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sentences (id, create_by_user, sentence, hint, seen_by_user) FROM stdin;
44	\N	SUPERARE ESAMI AL PRIMO COLPO	SPERANZA DEGLI STUDENTI	\N
45	\N	VINSERO BATTAGLIE GRAZIE ALLA LORO FOGA	LE AMAZZONI	\N
46	\N	LASCIA IL BATTERISTA D'ORAZIO	DIVORZIO IN CASA POOH	\N
47	\N	TENGO FAMIGLIA	MI GIUSTIFICO ALL'ITALIANA	\N
48	\N	EROE MARVEL IRONMAN	ROBERT DOWNEY JR	\N
49	\N	PRINCIPE SPOSTA UN'ATTRICE	MATRIMONIO REALE	\N
50	\N	FATTI MANDARE DALLA MAMMA	SUCCESSO DI MORANDI	\N
51	\N	NON POTEVANO RESTARE DA SOLI	I FIDANZATI DI UNA VOLTA	\N
55	\N	NON DIRE MAI COME FINISCE IL FILM	BON TON AL CINEMA	\N
59	\N	SONO QUELLE RUBATE	LE FOTO PIU' BELLE	\N
63	2	PARLARE DEL PIU' E DEL MENO	DIALOGO FRA MATEMATICI	\N
64	2	LE FACCINE DEGLI EMOTICON	ALLEGRIA! SUL CELLULARE	\N
65	2	NE DICONO IL DOPPIO DELLE DONNE	UOMINI E PAROLACCE	\N
66	2	PRIMA LA DOCCIA POI IL DOPOSOLE	TORNATI DAL MARE	\N
67	2	LA CANZONE DANCE SEXY	UN SUCCESSO STRABALLATO	\N
68	2	UN BRACCIALE CHE PROTEGGE DAL SOLE	NOVITA' SULLA SPIAGGIA	\N
69	2	MANO FREDDA CUORE CALDO	TIMIDEZZA PROVERBIALE	\N
70	2	SONO ARRIVATE LE STANGATE FISCALI	TASSE	\N
71	2	SIGLA DI TESTA E TITOLI DI CODA	L'INIZIO E LA FINE DI UN PROGRAMMA	\N
72	2	UN QUALUNQUISTA NON HA IDEE POLITICHE	UN MODO DI PENSARE	\N
\.


--
-- TOC entry 2891 (class 0 OID 16395)
-- Dependencies: 204
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, surname, mail, password, nickname, role, creation_date, last_change_date) FROM stdin;
4	utente1	cognome1	mail1@.it	1234	utente1	p	\N	2019-12-03 18:40:25.127159+01
5	utente2	cognome2	mail2@.it	1234	utente2	p	\N	2019-12-03 18:40:25.127773+01
6	utente3	cognome3	mail3@.it	1234	utente3	p	\N	2019-12-03 18:40:25.128309+01
7	utente4	cognome4	mail4@.it	1234	utente4	p	\N	2019-12-03 18:40:25.128896+01
8	utente5	cognome5	mail5@.it	1234	utente5	p	\N	2019-12-03 18:40:25.129438+01
9	utente6	cognome6	mail6@.it	1234	utente6	p	\N	2019-12-03 18:40:25.129884+01
10	utente7	cognome7	mail7@.it	1234	utente7	p	\N	2019-12-03 18:40:25.130407+01
11	utente8	cognome8	mail8@.it	1234	utente8	p	\N	2019-12-03 18:40:25.130897+01
12	utente9	cognome9	mail9@.it	1234	utente9	p	\N	2019-12-03 18:40:25.131371+01
2	dio	brando	i@i.it	boia	nick	a	\N	2019-11-24 19:20:04.702606+01
3	pippo	kujo	mail0@.it	1234	utente0	p	\N	2019-12-21 08:59:51.577419+01
\.


--
-- TOC entry 2898 (class 0 OID 16458)
-- Dependencies: 211
-- Data for Name: verifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.verifications (user_id, verification_code, user_mail) FROM stdin;
\.


--
-- TOC entry 2919 (class 0 OID 0)
-- Dependencies: 212
-- Name: Actions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Actions_id_seq"', 1, false);


--
-- TOC entry 2920 (class 0 OID 0)
-- Dependencies: 213
-- Name: Actions_manche_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Actions_manche_id_seq"', 1, false);


--
-- TOC entry 2921 (class 0 OID 0)
-- Dependencies: 214
-- Name: Actions_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Actions_player_id_seq"', 1, false);


--
-- TOC entry 2922 (class 0 OID 0)
-- Dependencies: 209
-- Name: manches_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.manches_id_seq', 1, false);


--
-- TOC entry 2923 (class 0 OID 0)
-- Dependencies: 205
-- Name: match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.match_id_seq', 5, true);


--
-- TOC entry 2924 (class 0 OID 0)
-- Dependencies: 207
-- Name: sentences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sentences_id_seq', 72, true);


--
-- TOC entry 2925 (class 0 OID 0)
-- Dependencies: 203
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 12, true);


--
-- TOC entry 2756 (class 2606 OID 16614)
-- Name: actions Actions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_pkey" PRIMARY KEY (id);


--
-- TOC entry 2752 (class 2606 OID 16565)
-- Name: manches manches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_pkey PRIMARY KEY (id);


--
-- TOC entry 2746 (class 2606 OID 16575)
-- Name: matches match_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matches
    ADD CONSTRAINT match_pkey PRIMARY KEY (id);


--
-- TOC entry 2748 (class 2606 OID 16504)
-- Name: sentences sentences_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_pkey PRIMARY KEY (id);


--
-- TOC entry 2750 (class 2606 OID 16457)
-- Name: sentences sentences_sentence_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_sentence_key UNIQUE (sentence);


--
-- TOC entry 2740 (class 2606 OID 16405)
-- Name: users user_mail_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_mail_key UNIQUE (mail);


--
-- TOC entry 2742 (class 2606 OID 16407)
-- Name: users user_nickname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_nickname_key UNIQUE (nickname);


--
-- TOC entry 2744 (class 2606 OID 16471)
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2754 (class 2606 OID 16492)
-- Name: verifications verifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT verifications_pkey PRIMARY KEY (verification_code, user_id);


--
-- TOC entry 2762 (class 2606 OID 16615)
-- Name: actions Actions_manche_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_manche_id_fkey" FOREIGN KEY (manche_id) REFERENCES public.manches(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2763 (class 2606 OID 16620)
-- Name: actions Actions_player_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_player_id_fkey" FOREIGN KEY (player_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2759 (class 2606 OID 16576)
-- Name: manches manches_match_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_match_id_fkey FOREIGN KEY (match_id) REFERENCES public.matches(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2758 (class 2606 OID 16539)
-- Name: manches manches_sentence_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_sentence_id_fkey FOREIGN KEY (sentence_id) REFERENCES public.sentences(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2757 (class 2606 OID 16518)
-- Name: sentences sentences_create_by_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_create_by_user_fkey FOREIGN KEY (create_by_user) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2761 (class 2606 OID 16748)
-- Name: verifications verification_mail_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT "verification_mail_FK" FOREIGN KEY (user_mail) REFERENCES public.users(mail) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2760 (class 2606 OID 16493)
-- Name: verifications verifications_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT verifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2019-12-23 14:01:44

--
-- PostgreSQL database dump complete
--

