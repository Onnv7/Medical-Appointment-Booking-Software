var express = require('express');
import { handleSingup, handleLogin, handleUpdateDetailInfor, handleTest } from '../controllers/patientController'


let router = express.Router()

router.post('/patient-signup', handleSingup)

router.post('/patient-login', handleLogin)

router.post('/patient-update-detail', handleUpdateDetailInfor)

router.get('/test', handleTest)

export default router;