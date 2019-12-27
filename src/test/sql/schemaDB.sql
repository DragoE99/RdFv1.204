--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2019-12-24 14:57:52

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
-- TOC entry 228 (class 1255 OID 16596)
-- Name: sentence_insert(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.sentence_insert(new_sentence character varying, new_hint character varying, creator integer DEFAULT NULL::integer) RETURNS integer
    
    AS ' 
    DECLARE
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
END;'
LANGUAGE PLPGSQL;


SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16607)
-- Name: actions; Type: TABLE; Schema: public; Owner: -
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


--
-- TOC entry 212 (class 1259 OID 16601)
-- Name: Actions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Actions_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 212
-- Name: Actions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Actions_id_seq" OWNED BY public.actions.id;


--
-- TOC entry 213 (class 1259 OID 16603)
-- Name: Actions_manche_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Actions_manche_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 213
-- Name: Actions_manche_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Actions_manche_id_seq" OWNED BY public.actions.manche_id;


--
-- TOC entry 214 (class 1259 OID 16605)
-- Name: Actions_player_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Actions_player_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 214
-- Name: Actions_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Actions_player_id_seq" OWNED BY public.actions.player_id;


--
-- TOC entry 210 (class 1259 OID 16437)
-- Name: manches; Type: TABLE; Schema: public; Owner: -
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


--
-- TOC entry 209 (class 1259 OID 16435)
-- Name: manches_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.manches_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 209
-- Name: manches_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.manches_id_seq OWNED BY public.manches.id;


--
-- TOC entry 206 (class 1259 OID 16410)
-- Name: matches; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.matches (
    id integer NOT NULL,
    state "char" NOT NULL,
    creator_id integer NOT NULL,
    user_id integer[],
    match_name character varying(255)
);


--
-- TOC entry 205 (class 1259 OID 16408)
-- Name: match_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 205
-- Name: match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.match_id_seq OWNED BY public.matches.id;


--
-- TOC entry 208 (class 1259 OID 16421)
-- Name: sentences; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sentences (
    id integer NOT NULL,
    create_by_user integer,
    sentence character varying(255) NOT NULL,
    hint character varying(255) NOT NULL,
    seen_by_user integer[]
);


--
-- TOC entry 207 (class 1259 OID 16419)
-- Name: sentences_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.sentences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 207
-- Name: sentences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.sentences_id_seq OWNED BY public.sentences.id;


--
-- TOC entry 204 (class 1259 OID 16395)
-- Name: users; Type: TABLE; Schema: public; Owner: -
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


--
-- TOC entry 203 (class 1259 OID 16393)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 203
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_id_seq OWNED BY public.users.id;


--
-- TOC entry 211 (class 1259 OID 16458)
-- Name: verifications; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.verifications (
    user_id integer NOT NULL,
    verification_code character varying(255) NOT NULL,
    user_mail character varying(255) NOT NULL
);


--
-- TOC entry 2735 (class 2604 OID 16610)
-- Name: actions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions ALTER COLUMN id SET DEFAULT nextval('public."Actions_id_seq"'::regclass);


--
-- TOC entry 2736 (class 2604 OID 16611)
-- Name: actions manche_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions ALTER COLUMN manche_id SET DEFAULT nextval('public."Actions_manche_id_seq"'::regclass);


--
-- TOC entry 2737 (class 2604 OID 16612)
-- Name: actions player_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions ALTER COLUMN player_id SET DEFAULT nextval('public."Actions_player_id_seq"'::regclass);


--
-- TOC entry 2731 (class 2604 OID 16563)
-- Name: manches id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.manches ALTER COLUMN id SET DEFAULT nextval('public.manches_id_seq'::regclass);


--
-- TOC entry 2729 (class 2604 OID 16573)
-- Name: matches id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.matches ALTER COLUMN id SET DEFAULT nextval('public.match_id_seq'::regclass);


--
-- TOC entry 2730 (class 2604 OID 16502)
-- Name: sentences id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sentences ALTER COLUMN id SET DEFAULT nextval('public.sentences_id_seq'::regclass);


--
-- TOC entry 2727 (class 2604 OID 16469)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 2756 (class 2606 OID 16614)
-- Name: actions Actions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_pkey" PRIMARY KEY (id);


--
-- TOC entry 2752 (class 2606 OID 16565)
-- Name: manches manches_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_pkey PRIMARY KEY (id);


--
-- TOC entry 2746 (class 2606 OID 16575)
-- Name: matches match_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.matches
    ADD CONSTRAINT match_pkey PRIMARY KEY (id);


--
-- TOC entry 2748 (class 2606 OID 16504)
-- Name: sentences sentences_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_pkey PRIMARY KEY (id);


--
-- TOC entry 2750 (class 2606 OID 16457)
-- Name: sentences sentences_sentence_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_sentence_key UNIQUE (sentence);


--
-- TOC entry 2740 (class 2606 OID 16405)
-- Name: users user_mail_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_mail_key UNIQUE (mail);


--
-- TOC entry 2742 (class 2606 OID 16407)
-- Name: users user_nickname_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_nickname_key UNIQUE (nickname);


--
-- TOC entry 2744 (class 2606 OID 16471)
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 2754 (class 2606 OID 16492)
-- Name: verifications verifications_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT verifications_pkey PRIMARY KEY (verification_code, user_id);


--
-- TOC entry 2762 (class 2606 OID 16615)
-- Name: actions Actions_manche_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_manche_id_fkey" FOREIGN KEY (manche_id) REFERENCES public.manches(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2763 (class 2606 OID 16620)
-- Name: actions Actions_player_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.actions
    ADD CONSTRAINT "Actions_player_id_fkey" FOREIGN KEY (player_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2759 (class 2606 OID 16576)
-- Name: manches manches_match_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_match_id_fkey FOREIGN KEY (match_id) REFERENCES public.matches(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2758 (class 2606 OID 16539)
-- Name: manches manches_sentence_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.manches
    ADD CONSTRAINT manches_sentence_id_fkey FOREIGN KEY (sentence_id) REFERENCES public.sentences(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2757 (class 2606 OID 16518)
-- Name: sentences sentences_create_by_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sentences
    ADD CONSTRAINT sentences_create_by_user_fkey FOREIGN KEY (create_by_user) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2761 (class 2606 OID 16748)
-- Name: verifications verification_mail_FK; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT "verification_mail_FK" FOREIGN KEY (user_mail) REFERENCES public.users(mail) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 2760 (class 2606 OID 16493)
-- Name: verifications verifications_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verifications
    ADD CONSTRAINT verifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2019-12-24 14:57:52

--
-- PostgreSQL database dump complete
--

