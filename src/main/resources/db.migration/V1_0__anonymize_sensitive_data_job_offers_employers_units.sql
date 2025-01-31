UPDATE slowniki.sl_placowka
SET nazwisko_kierownika = CONCAT('Kierownik_', md5(random()::text)::varchar(8))
WHERE nazwisko_kierownika IS NOT NULL;

UPDATE ofz.pracodawcy
SET pesel = LPAD((floor(random() * 100000000000)::bigint)::text, 11, '0')
WHERE pesel IS NOT NULL;

UPDATE ofz.oferty_pracy
SET imie_nazwisko_osoby_zgl = CONCAT('Osoba_', md5(random()::text)::varchar(8))
WHERE imie_nazwisko_osoby_zgl is not null;

UPDATE ofz.oferty_pracy
SET telefon_osoby_zgl = LPAD((floor(random() * 100000000)::bigint)::text, 9, '7')
WHERE telefon_osoby_zgl is not null;

UPDATE ofz.oferty_pracy
SET email_osoby_zgl = CONCAT('email_', md5(random()::text)::varchar(6), '@example.com')
WHERE email_osoby_zgl is not null;

UPDATE ofz.oferty_pracy
SET mentor_opiekun = CONCAT('Mentor_', md5(random()::text)::varchar(8))
WHERE mentor_opiekun is not null;


UPDATE ofz.oferty_pracy
SET imie_nazwisko_osoby_zgl_pracod = CONCAT('OsobaPracod_', md5(random()::text)::varchar(8))
WHERE imie_nazwisko_osoby_zgl_pracod is not null;

UPDATE ofz.oferty_pracy
SET telefon_osoby_zgl_pracod  = LPAD((floor(random() * 100000000)::bigint)::text, 9, '7')
WHERE telefon_osoby_zgl_pracod  is not null;

UPDATE ofz.oferty_pracy
SET email_osoby_zgl_pracod  = CONCAT('email_', md5(random()::text)::varchar(6), '@example.com')
WHERE email_osoby_zgl_pracod  is not null;