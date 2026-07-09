ALTER TABLE tb_parts
ADD COLUMN category_id UUID NOT NULL,
ADD CONSTRAINT fk_parts_category
    FOREIGN KEY (category_id)
    REFERENCES tb_category(category_id);