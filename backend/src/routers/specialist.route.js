import express from "express";
import { createSpecialist, removeSpecialist, updateSpecialist, getAllSpecialists } from "../controllers/specialist.controller.js"
const router = express.Router();

router.post("/create", createSpecialist);
router.get("/list", getAllSpecialists);
router.delete("/remove/:specialistId", removeSpecialist);
router.patch("/update/:specialistId", updateSpecialist);


export default router;