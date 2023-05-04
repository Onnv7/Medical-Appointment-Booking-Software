import express from "express";
import { registerNewPatient, loginPatient, registerNewDoctor, loginDoctor, sendCodeVerify, changePasswordPatient, changePasswordDoctor } from "../controllers/auth.controller.js";
const router = express.Router();

router.post("/patient/register", registerNewPatient);
router.post("/patient/login", loginPatient);
router.patch("/patient/change-password", changePasswordPatient);
router.patch("/doctor/change-password", changePasswordDoctor);
router.post("/doctor/register", registerNewDoctor);
router.post("/doctor/login", loginDoctor);

router.post("/send-confirmation-code", sendCodeVerify);
// router.patch("/change-password/:userId", changePassword);

export default router;
