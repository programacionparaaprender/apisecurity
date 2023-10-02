DO $$ 
BEGIN
  IF EXISTS 
  (
    SELECT
      rolname 
    FROM
      pg_roles 
    WHERE
      rolname = 'postgres_limited'
  )
THEN
  EXECUTE 'DROP OWNED BY postgres_limited cascade';
END
IF;
END
 $$ ;
 
DROP USER IF EXISTS postgres_limited;

CREATE USER postgres_limited WITH password 'postgres_limited';

GRANT CONNECT 
ON database postgres TO postgres_limited;

GRANT usage 
ON schema public TO postgres_limited;

GRANT 
SELECT, INSERT, UPDATE, DELETE
ON jdbc_customer, jdbc_merchant, jpa_customer TO postgres_limited;