CREATE SCHEMA IF NOT EXISTS veterinary_clinic
    AUTHORIZATION test_user;

CREATE TABLE IF NOT EXISTS veterinary_clinic.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    document_type character(3) COLLATE pg_catalog."default" NOT NULL,
    document_number integer NOT NULL,
    status integer NOT NULL,
    gender "char" NOT NULL,
    CONSTRAINT "PK_USERS" PRIMARY KEY (id),
    CONSTRAINT "UK_USERS_DOCUMENT_NUMBER" UNIQUE (document_number)
    )

    TABLESPACE pg_default;

ALTER TABLE veterinary_clinic.users
    OWNER to test_user;

GRANT ALL ON TABLE veterinary_clinic.users TO test_user;

CREATE TABLE IF NOT EXISTS veterinary_clinic.workers
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    position_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    speciality character varying(255) COLLATE pg_catalog."default" NOT NULL,
    document_type character(3) COLLATE pg_catalog."default" NOT NULL,
    document_number integer NOT NULL,
    CONSTRAINT workers_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE veterinary_clinic.workers
    OWNER to test_user;

GRANT ALL ON TABLE veterinary_clinic.workers TO test_user;

CREATE TABLE IF NOT EXISTS veterinary_clinic.pets
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    breed character varying(255) COLLATE pg_catalog."default" NOT NULL,
    gender "char" NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT "PK_PETS" PRIMARY KEY (id),
    CONSTRAINT "FK_USERS_PETS_USER_ID" FOREIGN KEY (user_id)
    REFERENCES veterinary_clinic.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE veterinary_clinic.pets
    OWNER to test_user;

GRANT ALL ON TABLE veterinary_clinic.pets TO test_user;

CREATE TABLE IF NOT EXISTS veterinary_clinic.clinic_histories
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    pet_id integer NOT NULL,
    created_at date NOT NULL,
    CONSTRAINT clinic_histories_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE veterinary_clinic.clinic_histories
    OWNER to test_user;

GRANT ALL ON TABLE veterinary_clinic.clinic_histories TO test_user;

CREATE TABLE IF NOT EXISTS veterinary_clinic.clinic_histories_detail
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    temperature numeric(19,2) NOT NULL,
    weight numeric(19,2) NOT NULL,
    heart_rate numeric(19,2) NOT NULL,
    breathing_rate numeric(19,2) NOT NULL,
    date_time timestamp with time zone NOT NULL,
                            feeding character varying(255) COLLATE pg_catalog."default" NOT NULL,
    habitat character varying(255) COLLATE pg_catalog."default" NOT NULL,
    observations character varying(255) COLLATE pg_catalog."default" NOT NULL,
    worker_id integer NOT NULL,
    clinic_history_id integer NOT NULL,
    CONSTRAINT clinic_histories_detail_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_CH_CLINIC_HISTORY_ID" FOREIGN KEY (clinic_history_id)
    REFERENCES veterinary_clinic.clinic_histories (id) MATCH SIMPLE
                        ON UPDATE NO ACTION
                        ON DELETE NO ACTION,
    CONSTRAINT "FK_WORKERS_CH_DETAIL_WORKER_ID" FOREIGN KEY (worker_id)
    REFERENCES veterinary_clinic.workers (id) MATCH SIMPLE
                        ON UPDATE NO ACTION
                        ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE veterinary_clinic.clinic_histories_detail
    OWNER to test_user;

GRANT ALL ON TABLE veterinary_clinic.clinic_histories_detail TO test_user;