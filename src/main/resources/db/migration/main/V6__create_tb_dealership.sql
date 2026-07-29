CREATE TABLE IF NOT EXISTS tb_dealership(

    dealer_id UUID NOT NULL,
    dealer_cnpj VARCHAR(14) NOT NULL,
    dealer_name VARCHAR(150) NOT NULL,
    dealer_trade_name VARCHAR(150) NOT NULL,
    dealer_email VARCHAR(150) NOT NULL,
    dealer_phone VARCHAR(15) NOT NULL,
    dealer_active BOOLEAN NOT NULL,
    zip_code VARCHAR(9) NOT NULL,
    street VARCHAR(150) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    complement VARCHAR(50),
    district VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state CHAR(2) NOT NULL,
    dealer_created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    dealer_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    dealer_created_by VARCHAR(255) NOT NULL,
    dealer_updated_by VARCHAR(255) NOT NULL,

    CONSTRAINT pk_tb_dealership PRIMARY KEY (dealer_id),

    CONSTRAINT uq_tb_dealership_cnpj UNIQUE (dealer_cnpj),
    CONSTRAINT uq_tb_dealership_name UNIQUE (dealer_name),
    CONSTRAINT uq_tb_dealership_email UNIQUE (dealer_email),

    CONSTRAINT chk_dealer_email_lower CHECK (dealer_email = LOWER(dealer_email))
);

CREATE INDEX idx_tb_dealership_city ON tb_dealership (city);
