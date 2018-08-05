 -- hibernate creates the following sequence (see when enabling debug)
 -- use it to increment primary key.

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
