import db from "../models";
import { handleSinupService, handleLoginService, updateDatePatientService, getTestService } from '../services/patientService'

export const handleSingup = async (req, res, next) => {
    try {
        let response = await handleSinupService(req.body)
        return res.status(200).json(response)
    } catch (error) {
        console.log(error)
        return res.status(200).json({
            errCode: -1,
            message: 'Error from server'
        })
    }
}

export const handleLogin = async (req, res, next) => {

    try {
        let email = req.body.email
        let password = req.body.password

        if (!email || !password) {
            return res.status(500).json(
                {
                    message: 'Vui lòng điền Email và Password',
                    errCode: 1,
                })
        }

        let userData = await handleLoginService(email, password)

        return res.status(200).json({
            errCode: userData.errCode,
            errMessage: userData.errMessage,
            user: userData.user ? userData.user : []
        })
    } catch (error) {
        next(error)
    }

}


export const handleUpdateDetailInfor = async (req, res, next) => {
    try {
        let data = req.body
        let message = await updateDatePatientService(data)
        return res.status(200).json(message)
    } catch (error) {
        next(error)
    }
}

export const handleTest = async (req, res) => {
    try {
        let id = req.body.id
        id = parseInt(id)
        let message = await getTestService(id)
        return res.status(200).json(message)
    } catch (error) {
        next(error)
    }
}
