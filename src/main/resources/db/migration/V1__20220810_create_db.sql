--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2022-08-10 19:45:37

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
-- TOC entry 8 (class 2615 OID 25830)
-- Name: veterinary_clinic; Type: SCHEMA; Schema: -; Owner: test_user
--

CREATE SCHEMA IF NOT EXISTS veterinary_clinic;


ALTER SCHEMA veterinary_clinic OWNER TO test_user;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 225 (class 1259 OID 25856)
-- Name: clinic_histories; Type: TABLE; Schema: veterinary_clinic; Owner: test_user
--

CREATE TABLE IF NOT EXISTS veterinary_clinic.clinic_histories (
    id integer NOT NULL,
    pet_id integer NOT NULL,
    created_at date NOT NULL
);


ALTER TABLE veterinary_clinic.clinic_histories OWNER TO test_user;

--
-- TOC entry 229 (class 1259 OID 25881)
-- Name: clinic_histories_detail; Type: TABLE; Schema: veterinary_clinic; Owner: test_user
--

CREATE TABLE IF NOT EXISTS veterinary_clinic.clinic_histories_detail (
    id integer NOT NULL,
    temperature numeric(19,2) NOT NULL,
    weight numeric(19,2) NOT NULL,
    heart_rate numeric(19,2) NOT NULL,
    breathing_rate numeric(19,2) NOT NULL,
    date_time timestamp with time zone NOT NULL,
    feeding character varying(255) NOT NULL,
    habitat character varying(255) NOT NULL,
    observations character varying(255) NOT NULL,
    worker_id integer NOT NULL,
    clinic_history_id integer NOT NULL
);


ALTER TABLE veterinary_clinic.clinic_histories_detail OWNER TO test_user;

--
-- TOC entry 226 (class 1259 OID 25861)
-- Name: clinic_histories_id_seq; Type: SEQUENCE; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE veterinary_clinic.clinic_histories ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME veterinary_clinic.clinic_histories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 25841)
-- Name: pets; Type: TABLE; Schema: veterinary_clinic; Owner: test_user
--

CREATE TABLE IF NOT EXISTS veterinary_clinic.pets (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    breed character varying(255) NOT NULL,
    gender "char" NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE veterinary_clinic.pets OWNER TO test_user;

--
-- TOC entry 224 (class 1259 OID 25854)
-- Name: pets_id_seq; Type: SEQUENCE; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE veterinary_clinic.pets ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME veterinary_clinic.pets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 25831)
-- Name: users; Type: TABLE; Schema: veterinary_clinic; Owner: test_user
--

CREATE TABLE IF NOT EXISTS veterinary_clinic.users (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    document_type character(3) NOT NULL,
    document_number integer NOT NULL,
    status integer NOT NULL,
    gender "char" NOT NULL
);


ALTER TABLE veterinary_clinic.users OWNER TO test_user;

--
-- TOC entry 228 (class 1259 OID 25865)
-- Name: workers; Type: TABLE; Schema: veterinary_clinic; Owner: test_user
--

CREATE TABLE IF NOT EXISTS veterinary_clinic.workers (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    position_name character varying(255) NOT NULL,
    speciality character varying(255) NOT NULL,
    document_type character(3) NOT NULL
);


ALTER TABLE veterinary_clinic.workers OWNER TO test_user;

--
-- TOC entry 227 (class 1259 OID 25863)
-- Name: workers_id_seq; Type: SEQUENCE; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE veterinary_clinic.workers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME veterinary_clinic.workers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2939 (class 2606 OID 25848)
-- Name: pets PK_PETS; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.pets
    ADD CONSTRAINT "PK_PETS" PRIMARY KEY (id);


--
-- TOC entry 2935 (class 2606 OID 25838)
-- Name: users PK_USERS; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.users
    ADD CONSTRAINT "PK_USERS" PRIMARY KEY (id);


--
-- TOC entry 2937 (class 2606 OID 25840)
-- Name: users UK_USERS_DOCUMENT_NUMBER; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.users
    ADD CONSTRAINT "UK_USERS_DOCUMENT_NUMBER" UNIQUE (document_number);


--
-- TOC entry 2945 (class 2606 OID 25888)
-- Name: clinic_histories_detail clinic_histories_detail_pkey; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.clinic_histories_detail
    ADD CONSTRAINT clinic_histories_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 25860)
-- Name: clinic_histories clinic_histories_pkey; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.clinic_histories
    ADD CONSTRAINT clinic_histories_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 25872)
-- Name: workers workers_pkey; Type: CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.workers
    ADD CONSTRAINT workers_pkey PRIMARY KEY (id);


--
-- TOC entry 2948 (class 2606 OID 25894)
-- Name: clinic_histories_detail FK_CH_CLINIC_HISTORY_ID; Type: FK CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.clinic_histories_detail
    ADD CONSTRAINT "FK_CH_CLINIC_HISTORY_ID" FOREIGN KEY (clinic_history_id) REFERENCES veterinary_clinic.clinic_histories(id);


--
-- TOC entry 2946 (class 2606 OID 25849)
-- Name: pets FK_USERS_PETS_USER_ID; Type: FK CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.pets
    ADD CONSTRAINT "FK_USERS_PETS_USER_ID" FOREIGN KEY (user_id) REFERENCES veterinary_clinic.users(id);


--
-- TOC entry 2947 (class 2606 OID 25889)
-- Name: clinic_histories_detail FK_WORKERS_CH_DETAIL_WORKER_ID; Type: FK CONSTRAINT; Schema: veterinary_clinic; Owner: test_user
--

ALTER TABLE ONLY veterinary_clinic.clinic_histories_detail
    ADD CONSTRAINT "FK_WORKERS_CH_DETAIL_WORKER_ID" FOREIGN KEY (worker_id) REFERENCES veterinary_clinic.workers(id);


-- Completed on 2022-08-10 19:45:38

--
-- PostgreSQL database dump complete
--

