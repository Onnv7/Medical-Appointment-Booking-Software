var express = require('express');
import { handleSingupDoctor } from '../controllers/doctorController'


let router = express.Router()

router.post('/doctor-signup', handleSingupDoctor)

export default router;