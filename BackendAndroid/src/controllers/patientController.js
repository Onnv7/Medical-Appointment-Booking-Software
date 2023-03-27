import db from "../models";
import patientService from '../services/patientService'

let handleSingup = async (req, res) => 
{
    try {
        let response = await patientService.handleSinupService(req.body)
        return res.status(200).json(response)
    } catch (error) {
        console.log(error)
        return res.status(200).json({
            errCode: -1,
            message: 'Error from server'
        })
    }
}

let handleLogin = async (req, res) => 
{

    let email = req.body.email
    let password = req.body.password

    if(!email || !password)
    {
        return res.status(500).json(
        {
            message: 'Vui lòng điền Email và Password',
            errCode: 1,
        })
    }

    let userData = await patientService.handleLoginService(email, password)
    
    return res.status(200).json({
        errCode: userData.errCode,
        errMessage: userData.errMessage,
        user: userData.user ? userData.user : []
    })

}


let handleUpdateDetailInfor= async (req,res) =>
{

   let data = req.body
   let message = await patientService.updateDatePatientService(data)
   return res.status(200).json(message)
}

let handleTest = async (req, res) => 
{
    let id = req.body.id
    id = parseInt(id)
    let message = await patientService.getTestService(id)
    return res.status(200).json(message)
}
module.exports = {
    handleSingup: handleSingup,
    handleLogin: handleLogin,
    handleUpdateDetailInfor:handleUpdateDetailInfor,
    handleTest:handleTest
}