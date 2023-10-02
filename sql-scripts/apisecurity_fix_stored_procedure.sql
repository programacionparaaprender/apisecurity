CREATE OR REPLACE PROCEDURE public.update_jdbc_customer(
  existing_customer_id bigint,
  new_full_name character varying)
LANGUAGE 'plpgsql'
AS $BODY$
begin
  update jdbc_customer set full_name = new_full_name 
   where customer_id = existing_customer_id;	
end
$BODY$;