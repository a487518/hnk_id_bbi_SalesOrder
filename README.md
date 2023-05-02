# hnk_id_bbi_SalesOrder
This project is to integrate the sales Order from DIS to BBI OrlanSoft ERP via Solace.
Data Flow
   DIS --> Solace --> Apache Camel --> OrlanSoft.
   
Orlansoft API uses basic authentication in every request to a service provided by using the username and password from Orlansoft
ERP that has been activated as API User.



Data that will be sent via Orlansoft API must be encrypted first using Base64 and the encrypted results are retrieved
The hash code is used as the hashCode parameter value, while the encrypted data is entered as the body.
   
   OrlanSoft End Point: https://mbitb.ap.ngrok.io/orlansoft-api/data-access/salesorder/createSalesOrder?hashCode=xxx
   
   Method : PUT
Parameter : hashCode = Hash code of encrypted Sales Order data
Body : Sales Order data that has been encrypted using Base64

The Sales Order data in JSON format that will be sent to Orlansoft below is a simple example with a few fields included. Column to be
included can be adjusted to your needs and a complete list of available columns is in the Sales Order Property section below

{
"SALES_ORDER": [
    {
        "trNo": "1900",
        "trDate": "2016-09-02",
        "trTime": "09:15:30",
        "trType": "111SO",
        "custBillTo": "1110000001"
    },
    [
        {
            "itemId": "11100001",
            "qt": 20,
            "unitId": "PCS",
            "description": "PUDING NANGKA",
            "unitPrice": 6000.00,
            "grossAmt": 120000.00,
            "discAmt": 12000.00,
            "taxable": 98182.00,
            "taxAmt": 9818.00,
            "netAmt": 108000.00
        },
        {
            "itemId": "11100002",
            "qt": 20,
            "unitId": "PCS",
            "description": "LEMPER PANGGANG",
            "unitPrice": 4000.00,
            "grossAmt": 80000.00,
            "discAmt": 8000.00,
            "taxable": 65455.00,
            "taxAmt": 6545.00,
            "netAmt": 72000.00
        }
    ]
]
}
