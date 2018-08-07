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

-- person table
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

-- phone table
set @PHONE1 = NEXTVAL('hibernate_sequence');
set @PHONE2 = NEXTVAL('hibernate_sequence');
set @PHONE3 = NEXTVAL('hibernate_sequence');

    insert
    into
        phone
        (phone_number, id)
    values
        ('703-111-1111', @PHONE1);

    insert
    into
        phone
        (phone_number, id)
    values
        ('703-222-2222', @PHONE2);

    insert
    into
        phone
        (phone_number, id)
    values
        ('703-333-3333', @PHONE3);

-- join table

        insert
    into
        person_phones
        (person_id, phones_id)
    values
        (@PERSON1, @PHONE1);

         insert
    into
        person_phones
        (person_id, phones_id)
    values
        (@PERSON1, @PHONE2);

         insert
    into
        person_phones
        (person_id, phones_id)
    values
        (@PERSON2, @PHONE3);
