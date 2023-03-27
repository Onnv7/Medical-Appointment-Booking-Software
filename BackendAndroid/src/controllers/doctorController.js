import db from "../models";
import { handleSinupDoctorService } from '../services/doctorService'

export const handleSingupDoctor = async (req, res, next) => {
    try {
        let response = await handleSinupDoctorService(req.body)
        return res.status(200).json(response)
    } catch (error) {
        next(error)
    }
}
