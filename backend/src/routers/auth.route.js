import express from "express";
import { registerNewPatient, loginPatient, registerNewDoctor, loginDoctor } from "../controllers/auth.controller.js";
const router = express.Router();

router.post("/patient/register", registerNewPatient);
router.post("/patient/login", loginPatient);
router.post("/doctor/register", registerNewDoctor);
router.post("/doctor/login", loginDoctor);
// router.patch("/change-password/:userId", changePassword);

export default router;
