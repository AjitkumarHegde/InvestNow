INSERT INTO sector (sector_name, description, created_at)  VALUES ('Services', 'Services', CURRENT_TIMESTAMP());
INSERT INTO sector (sector_name, description, created_at) VALUES ('Energy', 'Energy', CURRENT_TIMESTAMP());
INSERT INTO sector (sector_name, description, created_at) VALUES ('Financial', 'Financial', CURRENT_TIMESTAMP());
INSERT INTO sector (sector_name, description, created_at)  VALUES ('Construction', 'Construction', CURRENT_TIMESTAMP());
INSERT INTO sector (sector_name, description, created_at)  VALUES ('Technology', 'Technology', CURRENT_TIMESTAMP());
INSERT INTO sector (sector_name, description, created_at) VALUES ('Healthcare', 'Healthcare', CURRENT_TIMESTAMP());


INSERT INTO category (category_name, description, created_at) VALUES ('Large Cap', '20000 crore or more', CURRENT_TIMESTAMP());
INSERT INTO category (category_name, description, created_at) VALUES ('Mid Cap', '5000 to 20000 crore', CURRENT_TIMESTAMP());
INSERT INTO category (category_name, description, created_at) VALUES ('Flexi Cap', 'Combination of all three different types', CURRENT_TIMESTAMP());
INSERT INTO category (category_name, description, created_at) VALUES ('Small Cap', '5000 crore or less', CURRENT_TIMESTAMP());

INSERT INTO fund (fund_name, description, created_at, minimum_investment, rating, fund_manager, expense_ratio, exit_load, fund_size, yearly_returns_percentage, category, sector)
 VALUES ('Axxis Redchip fund direct plan growth', 'Axxis Redchip fund direct plan growth', CURRENT_TIMESTAMP(), '5000', '5', "Ramesh Naidu", '0.5', '0.005', '194434 cr', '15', 'Large Cap', 'Energy');
INSERT INTO fund (fund_name, description, created_at, minimum_investment, rating, fund_manager, expense_ratio, exit_load, fund_size, yearly_returns_percentage, category, sector)
 VALUES ('SBN Smallcap fund direct growth', 'SBN Smallcap fund direct growth', CURRENT_TIMESTAMP(), '2000', '4', "Suresh Naidu", '0.85', '0.005', '49943 cr', '12', 'Small Cap', 'Construction');
 INSERT INTO fund (fund_name, description, created_at, minimum_investment, rating, fund_manager, expense_ratio, exit_load, fund_size, yearly_returns_percentage, category, sector)
 VALUES ('Tatas Digital ind fund direct growth', 'Tatas Digital ind fund direct growth', CURRENT_TIMESTAMP(), '1000', '5', "Mahesh Naidu", '0.3', '0.005', '299404 cr', '17', 'Flexi Cap', 'Technology');
