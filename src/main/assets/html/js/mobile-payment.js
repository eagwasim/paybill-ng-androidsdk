function makePayment(data){
    var config = JSON.parse(data);

    config.onClose=function(reference){
        try{
            PayBillClient.onClose(reference);
        }catch(e){}
    }
   PayBillService.load(config);

}