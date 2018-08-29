 -- hibernate creates the following sequence (see when enabling debug)
 -- use it to increment primary key.
/*
    insert
    into
        my_db_entity
        (age, first_name, last_name, id)
    values
        (50, 'steve', 'souza', NEXTVAL('hibernate_sequence'));

    insert
    into
        my_db_entity
        (age, first_name, last_name, id)
    values
        (80, 'joe', 'souza', NEXTVAL('hibernate_sequence'));

    insert
    into
        my_db_entity
        (age, first_name, last_name, id)
    values
        (35, 'jon', 'doe', NEXTVAL('hibernate_sequence'));
        */

-- person
set @PERSON1 = NEXTVAL('hibernate_sequence');
set @PERSON2 = NEXTVAL('hibernate_sequence');



  insert
    into
        person
        (age, first_name, last_name, id)
    values
        (50, 'steve', 'souza', @PERSON1);

    insert
    into
        person
        (age, first_name, last_name, id)
    values
        (80, 'joe', 'souza', @PERSON2);

-- phone
set @PHONE1 = NEXTVAL('hibernate_sequence');
set @PHONE2 = NEXTVAL('hibernate_sequence');
set @PHONE3 = NEXTVAL('hibernate_sequence');

    insert
    into
        phone
        (phone_number, person_id, id)
    values
        ('703-111-1111', @PERSON1, @PHONE1);

    insert
    into
        phone
        (phone_number, person_id, id)
    values
        ('703-222-2222', @PERSON1, @PHONE2);

    insert
    into
        phone
        (phone_number, person_id, id)
    values
        ('703-333-3333', @PERSON2, @PHONE3);

-- person_phones

--         insert
--     into
--         person_phones
--         (person_id, phones_id)
--     values
--         (@PERSON1, @PHONE1);
--
--          insert
--     into
--         person_phones
--         (person_id, phones_id)
--     values
--         (@PERSON1, @PHONE2);
--
--          insert
--     into
--         person_phones
--         (person_id, phones_id)
--     values
--         (@PERSON2, @PHONE3);


 -- certification



set @CERT1 = NEXTVAL('hibernate_sequence');
set @CERT2 = NEXTVAL('hibernate_sequence');
set @CERT3 = NEXTVAL('hibernate_sequence');

    insert
    into
        certification
        (id, certification_name)
    values
        (@CERT1, 'java');

    insert
    into
        certification
        (id, certification_name)
    values
        (@CERT2, 'groovy');

   insert
    into
        certification
        (id, certification_name)
    values
        (@CERT3, 'guitar');


 -- person_certification

   insert
   into
        person_certification
        (id, location, person_id, certification_id)
    values
        (NEXTVAL('hibernate_sequence'), 'arlington', @PERSON1, @CERT1);

   insert
    into
        person_certification
        (id, location, person_id, certification_id)
    values
        (NEXTVAL('hibernate_sequence'), 'amsterdam', @PERSON1, @CERT2);

   insert
    into
        person_certification
        (id, location, person_id, certification_id)
    values
        (NEXTVAL('hibernate_sequence'), 'va beach', @PERSON2, @CERT1);

   insert
    into
        person_certification
        (id, location, person_id, certification_id)
    values
        (NEXTVAL('hibernate_sequence'), 'va beach', @PERSON2, @CERT3);

-- if you need to reset the hibernate sequence starting point
-- ALTER SEQUENCE hibernate_sequence RESTART WITH 200

