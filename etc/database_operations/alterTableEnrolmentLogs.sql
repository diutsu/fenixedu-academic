RENAME TABLE ENROLMENT_LOG TO CURRICULUM_LINE_LOG;
ALTER TABLE CURRICULUM_LINE_LOG ADD COLUMN CREDITS text, ADD COLUMN SOURCE_DESCRIPTION text, CHANGE COLUMN KEY_CURRICULAR_COURSE KEY_DEGREE_MODULE INT(11) NOT NULL, DROP KEY KEY_CURRICULAR_COURSE, ADD KEY (KEY_DEGREE_MODULE);
