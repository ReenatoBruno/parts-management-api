INSERT INTO tb_parts_api(
                         part_number,
                         part_name,
                         price,
                         quantity,
                         supplier,
                         part_description,
                         part_created_at,
                         part_updated_at
)

VALUES (
        '9BHBG-3710',
        'Aluminum Screw',
        129.99,
        1,
        'Metal Works Ltd',
        'Aluminum screw 1/8 inch',
        NOW() AT TIME ZONE 'UTC',
        NOW() AT TIME ZONE 'UTC'
       );