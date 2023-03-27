var express = require('express');
import patientController from '../../controllers/patientController'


let router = express.Router()
let initWebRoutesPatient = (app) => 
{

    router.post('/api/patient-signup', patientController.handleSingup)

    router.post('/api/patient-login', patientController.handleLogin)

    router.post('/api/patient-update-detail', patientController.handleUpdateDetailInfor)

    router.get('/api/test', patientController.handleTest)
    
    return app.use("/", router)
}

module.exports = initWebRoutesPatient