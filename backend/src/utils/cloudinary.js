import { v2 as cloudinary } from "cloudinary";
import dotenv from "dotenv";
dotenv.config();

cloudinary.config({
    cloud_name: process.env.CLOUDINARY_NAME,
    api_key: process.env.CLOUDINARY_API_KEY,
    api_secret: process.env.CLOUDINARY_API_SECRET
})

export const uploadImage = async (fileStr, folder) => {
    try {
        const uploadedResponse = await cloudinary.uploader.upload(fileStr, {
            upload_preset: folder
        })
        return uploadedResponse;
    } catch (error) {

    }
}
export default cloudinary