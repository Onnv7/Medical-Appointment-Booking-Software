import express from "express";
import { createPeriod, updateAvatarDoctorDefault } from "../controllers/admin.controller.js"
const router = express.Router();

router.post('create-period', createPeriod)
router.put("/update-avatar/default", upload.single('image'), updateAvatarDoctorDefault);
export default router;