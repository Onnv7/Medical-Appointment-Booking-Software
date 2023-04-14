import express from "express";
import { createPeriod, updateAvatarDoctorDefault, updateAvatarPatientDefault } from "../controllers/admin.controller.js"

import multer from 'multer';
const router = express.Router();

const storage = multer.memoryStorage();
const upload = multer({ storage });

router.post('/create-period', createPeriod)
router.put("/doctor/update-avatar/default", upload.single('image'), updateAvatarDoctorDefault);
router.put("/patient/update-avatar/default", upload.single('image'), updateAvatarPatientDefault);
router.post('/create/period', createPeriod)
export default router;