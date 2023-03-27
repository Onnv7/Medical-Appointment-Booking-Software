var express = require('express');
import doctorController from '../../controllers/doctorController'


let router = express.Router()
let initWebRoutesDoctor = (app) => 
{

    router.post('/api/doctor-signup', doctorController.handleSingupDoctor)

    // router.post('/api/patient-login', doctorController.handleLoginDoctor)
    
    return app.use("/", router)
}

module.exports = initWebRoutesDoctor