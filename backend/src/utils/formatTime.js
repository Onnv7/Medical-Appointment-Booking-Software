import moment from "moment";
export const formatMongooseTime = (type, time) => {

    const formattedDate = moment(time).format(type);

    return formattedDate
}