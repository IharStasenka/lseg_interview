select * from customers c join orders o on o.customer_id = c.id;

update customers set product = 'corn' where id = 2;

select sum(country_amount) from customers where country = 'UK';