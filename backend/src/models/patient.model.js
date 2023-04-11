import mongoose from "mongoose";

const patientSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true
    },
    password: {
        type: String
    },
    name: {
        type: String
    },
    gender: {
        type: String,
        enum: ["male", "female"],
    },
    birthDate: {
        type: Date
    },
    avatarUrl: {
        type: String
    },
    phone: {
        type: String
    },
    address: {
        type: String
    }
}, { timestamps: true });

export default mongoose.model("Patient", patientSchema);