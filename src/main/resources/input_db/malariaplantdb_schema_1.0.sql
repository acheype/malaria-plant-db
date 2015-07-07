-- execute with malariaplantdb privileges once connected at malariaplantdb database
-- command : psql -h localhost -p 5432 -d malariaplantdb -U malariaplantdb -f malariaplantdb_schema_1.0.sql

BEGIN;

CREATE TABLE author (
  id             INT8         NOT NULL,
  publication_id INT8         NOT NULL,
  family         VARCHAR(255) NOT NULL,
  given          VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE compiler (
  DTYPE               VARCHAR(31)  NOT NULL,
  id                  INT8         NOT NULL,
  family              VARCHAR(255) NOT NULL,
  given               VARCHAR(255) NOT NULL,
  email               VARCHAR(255) NOT NULL,
  institution         VARCHAR(255),
  institution_address VARCHAR(255),
  password            VARCHAR(255),
  PRIMARY KEY (id),
  CHECK (DTYPE <> 'Administrator' OR password IS NOT NULL)
);

ALTER TABLE compiler
ADD CONSTRAINT uk_email UNIQUE (email);


CREATE TABLE publication (
  id              INT8         NOT NULL,
  entry_type      VARCHAR(255) NOT NULL,
  year            INT4         NOT NULL CHECK (year >= 0 AND year <= 3000),
  title           VARCHAR(255) NOT NULL,
  journal         VARCHAR(255),
  month           VARCHAR(255),
  page            VARCHAR(255),
  volume          VARCHAR(255),
  nb_of_volumes   VARCHAR(255),
  number          VARCHAR(255),
  book_title      VARCHAR(255),
  publisher       VARCHAR(255),
  edition         VARCHAR(255),
  university      VARCHAR(255),
  institution     VARCHAR(255),
  doi             VARCHAR(255),
  pmid            VARCHAR(255),
  isbn            VARCHAR(255),
  url             TEXT,
  is_reviewed     BOOLEAN      NOT NULL,
  compilers_notes TEXT,
  PRIMARY KEY (id)
);

ALTER TABLE publication
ADD CONSTRAINT uk_title UNIQUE (title);

ALTER TABLE author
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;

CREATE TABLE publication_compilers (
  compiler_id    INT8 NOT NULL,
  publication_id INT8 NOT NULL
);

ALTER TABLE publication_compilers
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES compiler;

ALTER TABLE publication_compilers
ADD CONSTRAINT fk_compiler_id
FOREIGN KEY (compiler_id)
REFERENCES publication;


CREATE TABLE species (
  id                   INT8         NOT NULL,
  publication_id       INT8         NOT NULL,
  family               VARCHAR(255) NOT NULL,
  species              VARCHAR(255) NOT NULL,
  species_name_in_pub  VARCHAR(255) NOT NULL,
  is_herbarium_voucher BOOLEAN      NOT NULL,
  herbarium            VARCHAR(255),
  local_name           VARCHAR(255),
  collection_site      VARCHAR(255),
  country              VARCHAR(255) NOT NULL,
  continent            VARCHAR(255),
  PRIMARY KEY (id)
);

ALTER TABLE species
ADD CONSTRAINT uk_pub_species UNIQUE (publication_id, species);

ALTER TABLE species
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;


CREATE TABLE plant_ingredient (
  id                   INT8         NOT NULL,
  species_id           INT8         NOT NULL,
  part_used            VARCHAR(255) NOT NULL,
  ethnology_id         INT8,
  in_vivo_pharmaco_id  INT8,
  in_vitro_pharmaco_id INT8,
  clinical_id          INT8,
  PRIMARY KEY (id)
);

ALTER TABLE plant_ingredient
ADD CONSTRAINT fk_species_id
FOREIGN KEY (species_id)
REFERENCES species;


CREATE TABLE ethnology (
  id                         INT8         NOT NULL,
  publication_id             INT8         NOT NULL,
  ethno_relevancy            TEXT         NOT NULL,
  ethno_relevancy_ref_id     INT8,
  treatment_type             VARCHAR(255) NOT NULL,
  is_traditional_recipe      BOOLEAN      NOT NULL,
  traditional_recipe_details TEXT,
  preparation_mode           VARCHAR(255),
  administration_route       VARCHAR(255),
  PRIMARY KEY (id)
);

ALTER TABLE ethnology
ADD CONSTRAINT fk_publication_relevancy_id
FOREIGN KEY (ethno_relevancy_ref_id)
REFERENCES publication;

ALTER TABLE ethnology
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;

ALTER TABLE plant_ingredient
ADD CONSTRAINT fk_ethnology_id
FOREIGN KEY (ethnology_id)
REFERENCES ethnology;


CREATE TABLE in_vivo_pharmaco (
  id                     INT8         NOT NULL,
  publication_id         INT8         NOT NULL,
  tested_entity          VARCHAR(255) NOT NULL,
  extraction_solvent     VARCHAR(255),
  additive_product       VARCHAR(255),
  compound_name          VARCHAR(255),
  screening_test         VARCHAR(255) NOT NULL,
  parasite               VARCHAR(255) NOT NULL,
  parasite_details       VARCHAR(255),
  animal                 VARCHAR(255),
  treatment_route        VARCHAR(255),
  dose                   FLOAT4,
  inhibition             FLOAT4 CHECK (inhibition <= 100 AND inhibition >= 0),
  survival_percent       FLOAT4 CHECK (survival_percent <= 100 AND survival_percent >= 0),
  survival_time          FLOAT4,
  ed50                   FLOAT4,
  is_toxicity            BOOLEAN      NOT NULL,
  ld50                   FLOAT4,
  compilers_observations TEXT,
  PRIMARY KEY (id)
);

ALTER TABLE in_vivo_pharmaco
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;

ALTER TABLE plant_ingredient
ADD CONSTRAINT fk_in_vivo_pharmaco_id
FOREIGN KEY (in_vivo_pharmaco_id)
REFERENCES in_vivo_pharmaco;


CREATE TABLE in_vitro_pharmaco (
  id                     INT8         NOT NULL,
  publication_id         INT8         NOT NULL,
  tested_entity          VARCHAR(255) NOT NULL,
  extraction_solvent     VARCHAR(255),
  additive_product       VARCHAR(255),
  compound_name          VARCHAR(255),
  screening_test         VARCHAR(255) NOT NULL,
  parasite               VARCHAR(255) NOT NULL,
  parasite_details       VARCHAR(255),
  cell_line              VARCHAR(255),
  measure_method         VARCHAR(255),
  concentration          FLOAT4,
  inhibition             FLOAT4 CHECK (inhibition <= 100 AND inhibition >= 0),
  ic50                   FLOAT4,
  is_toxicity            BOOLEAN      NOT NULL,
  tc50                   FLOAT4,
  compilers_observations TEXT,
  PRIMARY KEY (id)
);

ALTER TABLE in_vitro_pharmaco
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;

ALTER TABLE plant_ingredient
ADD CONSTRAINT fk_in_vitro_pharmaco_id
FOREIGN KEY (in_vitro_pharmaco_id)
REFERENCES in_vitro_pharmaco;


CREATE TABLE clinical (
  id             INT8 NOT NULL,
  publication_id INT8 NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE clinical
ADD CONSTRAINT fk_publication_id
FOREIGN KEY (publication_id)
REFERENCES publication;

ALTER TABLE plant_ingredient
ADD CONSTRAINT fk_clinical_id
FOREIGN KEY (clinical_id)
REFERENCES clinical;

CREATE TABLE hibernate_unique_key (
  next_hi INT4
);

INSERT INTO hibernate_unique_key VALUES (0);


COMMIT;
