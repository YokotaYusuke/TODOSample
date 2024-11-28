export default interface Http {
    get(url: string): Promise<string[]>
    post(url: string, body: string): void
}

export class NetWorkHttp implements Http {
    async get(url: string): Promise<string[]> {
        return await fetch(url)
            .then(res => res.json())
            .catch(error => console.log(error))
    }

    post(url: string, body: string): void {
        const headers = {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            },
            body: body
        }
        fetch(url, headers)
    }


}