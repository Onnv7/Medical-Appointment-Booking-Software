import db from "../models";
import doctorService from '../services/doctorService'

let handleSingupDoctor = async (req, res) => 
{
    try {
        let response = await doctorService.handleSinupDoctorService(req.body)
        return res.status(200).json(response)
    } catch (error) {
        console.log(error)
        return res.status(200).json({
            errCode: -1,
            message: 'Error from server'
        })
    }
}

module.exports = {
    handleSingupDoctor:handleSingupDoctor
}