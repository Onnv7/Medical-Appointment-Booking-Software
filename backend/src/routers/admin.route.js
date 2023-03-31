import express from "express";
import { createPeriod } from "../controllers/admin.controller.js"
const router = express.Router();

router.post('create-period', createPeriod)

export default router;